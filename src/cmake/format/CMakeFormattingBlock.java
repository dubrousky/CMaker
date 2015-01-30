package cmake.format;

import cmake.global.CMakeLanguage;
import cmake.psi.*;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
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
public class CMakeFormattingBlock extends AbstractBlock implements BlockEx {
    public static final TokenSet BLOCKS_TOKEN_SET = TokenSet.create(
            CMakeTypes.BODY
    );
    private final Indent myIndent;
    private final SpacingBuilder mySpacingBuilder;
    private List<Block> mySubBlocks;

    public CMakeFormattingBlock(@NotNull ASTNode node,
                                @Nullable Wrap wrap,
                                @Nullable Alignment alignment,
                                @NotNull SpacingBuilder spacingBuilder
    ) {
        super(node, wrap, alignment);
        mySpacingBuilder = spacingBuilder;
        myIndent = new CMakeIndentProcessor().getChildIndent(node);
    }


    @NotNull
    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<Block>();
        ASTNode child = myNode.getFirstChildNode();
        ASTNode previousChild = null;
        while (child != null) {
            if (shouldCreateBlockFor(child)) {
                Block block = new CMakeFormattingBlock(child, 
                        Wrap.createWrap(WrapType.NONE, false),
                        Alignment.createAlignment(),
                        mySpacingBuilder);
                blocks.add(block);
            }
            previousChild = child;
            child = child.getTreeNext();
        }
        return blocks;
    }

    boolean shouldCreateBlockFor(ASTNode astNode) {
        return astNode.getTextRange().getLength() != 0
               && astNode.getElementType() != TokenType.WHITE_SPACE;
                
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
    protected Indent getChildIndent() { return null;}

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @Nullable
    @Override
    public Language getLanguage() {
        return CMakeLanguage.INSTANCE;
    }
}
