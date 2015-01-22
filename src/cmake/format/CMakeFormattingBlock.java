package cmake.format;

import cmake.psi.*;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.WrappingUtil;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeFormattingBlock extends AbstractBlock {
    public static final TokenSet BLOCKS_TOKEN_SET = TokenSet.create(
            CMakeTypes.BODY
    );
    private final Indent myIndent;
    private final SpacingBuilder mySpacingBuilder;
    private List<Block> mySubBlocks;

    public CMakeFormattingBlock(@NotNull ASTNode node,
                                @Nullable Alignment alignment,
                                @Nullable Wrap wrap,
                                @NotNull SpacingBuilder spacingBuilder
    ) {
        super(node, wrap, alignment);
        mySpacingBuilder = spacingBuilder;
        myIndent = new CMakeIndentProcessor().getChildIndent(node);
    }


    @NotNull
    @Override
    protected List<Block> buildChildren() {
        if (mySubBlocks == null) {
            mySubBlocks = buildSubBlocks();
        }
        return new ArrayList<Block>(mySubBlocks);
    }

    private List<Block> buildSubBlocks() {
        final List<Block> blocks = new ArrayList<Block>();
        final Alignment baseAlignment = Alignment.createAlignment(true);
        final Alignment baseAlignment2 = Alignment.createChildAlignment(baseAlignment);
        final Ref<Wrap> chopDownIfLongWrap = new Ref<Wrap>();

        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            if (!shouldCreateBlockFor(child)) continue;
            blocks.add(createChildBlock(myNode, child, chopDownIfLongWrap, baseAlignment, baseAlignment2));
        }
        return Collections.unmodifiableList(blocks);
    }

    boolean shouldCreateBlockFor(ASTNode astNode) {
        return astNode.getTextRange().getLength() != 0 
                && astNode.getElementType() != TokenType.WHITE_SPACE;
                
    }

    private CMakeFormattingBlock createChildBlock(ASTNode parent,
                                                  ASTNode child,
                                                  Ref<Wrap> chopDownIfLongWrap,
                                                  Alignment baseAlignment,
                                                  Alignment baseAlignment2) {
        Alignment alignment = getAlignment(parent, child, baseAlignment, baseAlignment2);
        WrapType wrapType = calculateWrapType(parent, child);
        Wrap wrap;
        if (wrapType == WrapType.CHOP_DOWN_IF_LONG) {
            if (chopDownIfLongWrap.isNull()) {
                chopDownIfLongWrap.set(Wrap.createWrap(wrapType, true));
            }
            wrap = chopDownIfLongWrap.get();
        } else if (wrapType == null) {
            wrap = null;
        } else {
            wrap = Wrap.createWrap(wrapType, true);
        }
        return new CMakeFormattingBlock(child, alignment, wrap, mySpacingBuilder);
    }


    @Nullable
    private WrapType calculateWrapType(@NotNull ASTNode parent, @NotNull ASTNode node) {
        IElementType parentType = parent.getElementType();
        PsiElement nodePsi = node.getPsi();
        PsiElement parentPsi = parent.getPsi();
        if (parentType == CMakeTypes.ARGUMENTS && nodePsi instanceof CMakeArgument) {
            return WrappingUtil.getWrapType(CommonCodeStyleSettings.WRAP_AS_NEEDED);
        }
        else if (parentType == CMakeTypes.COMPOUND_EXPR && nodePsi instanceof CMakeCommandExpr) {
            return WrappingUtil.getWrapType(CommonCodeStyleSettings.WRAP_AS_NEEDED);
        }
        return null;
    }

    @Nullable
    private Alignment getAlignment(@NotNull ASTNode parent, @NotNull ASTNode child,
                                   @Nullable Alignment baseAlignment,
                                   @Nullable Alignment baseAlignment2) {
        IElementType childType = child.getElementType();
        IElementType parentType = parent.getElementType();
        if (parent.getPsi() instanceof CMakeBody)
            return baseAlignment2;
        return baseAlignment;
    }


    @Nullable
    @Override
    public Spacing getSpacing(Block block, Block block1) {
        return mySpacingBuilder.getSpacing(this, block, block1);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {

        return new ChildAttributes(myIndent, Alignment.createChildAlignment(Alignment.createAlignment()));
    }

    @Override
    public Indent getIndent() {
        return myIndent;
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
