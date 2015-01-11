package cmake.format;

import cmake.global.CMakeParserDefinition;
import cmake.psi.CMakeArgument;
import cmake.psi.CMakeTypes;
import com.intellij.formatting.*;
import com.intellij.formatting.alignment.AlignmentStrategy;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
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
    public  static final TokenSet BLOCKS_TOKEN_SET = TokenSet.create();
    private final Indent myIndent;
    private final AlignmentStrategy myAlignmentStrategy;
    private final CommonCodeStyleSettings mySettings;
    private final CMakeCodeStyleSettings myCMakeSettings;
    private final SpacingBuilder mySpacingBuilder;
    private List<Block> mySubBlocks;

    public CMakeFormattingBlock(@NotNull ASTNode node,
                                @Nullable Alignment alignment,
                                @Nullable AlignmentStrategy alignmentStrategy,
                                @Nullable Wrap wrap,
                                @NotNull CommonCodeStyleSettings settings,
                                @NotNull CMakeCodeStyleSettings cmakeSettings,
                                @NotNull SpacingBuilder spacingBuilder,
                                int binaryExpressionIndex) {
        super(node, wrap, alignment);
        myAlignmentStrategy = alignmentStrategy;
        mySettings = settings;
        myCMakeSettings = cmakeSettings;
        mySpacingBuilder = spacingBuilder;
        myIndent = new CMakeIndentProcessor(cmakeSettings).getChildIndent(node, binaryExpressionIndex);
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
        final Alignment baseAlignment2 = Alignment.createAlignment(true);
        final AlignmentStrategy alignmentStrategy = null;//createOrGetAlignmentStrategy();
        final Ref<Wrap> chopDownIfLongWrap = new Ref<Wrap>();

        // if uniform binary expressions option is enabled, blocks for binary expression sequences are built flat, that is
        // for an expression like 1 + 1 + 1 a single parent block with 5 children in it is constructed.
//        if (myErlangSettings.UNIFORM_BINARY_EXPRESSIONS && BINARY_EXPRESSIONS.contains(myNode.getElementType())) {
//            class BinaryExpressionSequenceBlocksBuilder {
//                private int myBinaryExpressionIndex = 0;
//                void build(ASTNode node) {
//                    for (ASTNode child = node.getFirstChildNode(); child != null; child = child.getTreeNext()) {
//                        if (!shouldCreateBlockFor(child)) continue;
//                        IElementType childType = child.getElementType();
//                        if (BINARY_EXPRESSIONS.contains(childType) ||
//                                myBinaryExpressionIndex != 0 &&
//                                        childType == ERL_MAX_EXPRESSION &&
//                                        child.getFirstChildNode() != null &&
//                                        child.getFirstChildNode().getElementType() == ERL_STRING_LITERAL) {
//                            build(child);
//                        }
//                        else {
//                            blocks.add(createChildBlock(node, child, chopDownIfLongWrap, baseAlignment, baseAlignment2, alignmentStrategy, myBinaryExpressionIndex));
//                            myBinaryExpressionIndex++;
//                        }
//                    }
//                }
//            }
//            new BinaryExpressionSequenceBlocksBuilder().build(myNode);
//        }
//        else {
            for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
                if (!shouldCreateBlockFor(child)) continue;
                blocks.add(createChildBlock(myNode, child, chopDownIfLongWrap, baseAlignment, baseAlignment2, alignmentStrategy, -1));
            }
//        }
        return Collections.unmodifiableList(blocks);
    }

    boolean shouldCreateBlockFor(ASTNode astNode)
    {
        //return node.getTextRange().getLength() != 0 && node.getElementType() != TokenType.WHITE_SPACE;
        if(CMakeTypes.COMPOUND_EXPR == astNode.getElementType()) {

            return true;
        }
        return false;
    }

    private CMakeFormattingBlock createChildBlock(ASTNode parent,
                                           ASTNode child,
                                           Ref<Wrap> chopDownIfLongWrap,
                                           Alignment baseAlignment,
                                           Alignment baseAlignment2,
                                           @Nullable AlignmentStrategy alignmentStrategy,
                                           int binaryExpressionIndex) {
        Alignment alignment = getAlignment(parent, child, baseAlignment, baseAlignment2, binaryExpressionIndex);
        WrapType wrapType = calculateWrapType(parent, child);
        Wrap wrap;
        if (wrapType == WrapType.CHOP_DOWN_IF_LONG) {
            if (chopDownIfLongWrap.isNull()) {
                chopDownIfLongWrap.set(Wrap.createWrap(wrapType, true));
            }
            wrap = chopDownIfLongWrap.get();
        }
        else if (wrapType == null) {
            wrap = null;
        }
        else {
            wrap = Wrap.createWrap(wrapType, true);
        }
        return new CMakeFormattingBlock(child, alignment, alignmentStrategy, wrap, mySettings, myCMakeSettings, mySpacingBuilder, binaryExpressionIndex);
    }


    @Nullable
    private WrapType calculateWrapType(@NotNull ASTNode parent, @NotNull ASTNode node) {
        IElementType parentType = parent.getElementType();
        PsiElement nodePsi = node.getPsi();
        PsiElement parentPsi = parent.getPsi();
// //       if (parentType == ERL_CLAUSE_BODY && nodePsi instanceof ErlangExpression) {
// //           return WrappingUtil.getWrapType(myCMakeSettings.EXPRESSION_IN_CLAUSE_WRAP);
// //       }
        if (parentType ==CMakeTypes.ARGUMENTS && nodePsi instanceof CMakeArgument) {
            return WrappingUtil.getWrapType(mySettings.CALL_PARAMETERS_WRAP);
        }
        return null;
    }

    @Nullable
    private Alignment getAlignment(@NotNull ASTNode parent, @NotNull ASTNode child,
                                   @Nullable Alignment baseAlignment, 
                                   @Nullable Alignment baseAlignment2, 
                                   int binaryExpressionIndex) {
        IElementType childType = child.getElementType();
        IElementType parentType = parent.getElementType();
//        Alignment fromStrategy = calculateAlignmentFromStrategy(parent, child);
//        if (fromStrategy != null) return fromStrategy;
//
//        if (PARENTHESIS_CONTAINERS.contains(parentType)) {
//            if (childType != ERL_PAR_LEFT && childType != ERL_PAR_RIGHT && childType != ERL_COMMA) {if (myErlangSettings.ALIGN_MULTILINE_BLOCK) return baseAlignment;}
//            else if (myErlangSettings.NEW_LINE_BEFORE_COMMA) return baseAlignment2;
//        }
//        if (CURLY_CONTAINERS.contains(parentType)) {
//            if (childType != ERL_CURLY_LEFT && childType != ERL_CURLY_RIGHT && childType != ERL_COMMA && childType != ERL_RADIX) {
//                if (myErlangSettings.ALIGN_MULTILINE_BLOCK) return baseAlignment;
//            }
//            else if (myErlangSettings.NEW_LINE_BEFORE_COMMA) return baseAlignment2;
//        }
//        if (BRACKETS_CONTAINERS.contains(parentType)) {
//            if (childType != ERL_BRACKET_LEFT && childType != ERL_BRACKET_RIGHT && childType != ERL_BIN_START && childType != ERL_BIN_END &&
//                    childType != ERL_COMMA && childType != ERL_OP_OR) {if (myErlangSettings.ALIGN_MULTILINE_BLOCK) return baseAlignment;}
//            else if (myCmakeSettings.NEW_LINE_BEFORE_COMMA) return baseAlignment2;
//        }
//        if (myCMakeSettings.ALIGN_MULTILINE_BLOCK) {
//            if (parentType == ERL_LIST_COMPREHENSION) {
//                boolean bracketsCurliesOrComma = childType == ERL_BRACKET_LEFT || childType == ERL_BRACKET_RIGHT || childType == ERL_COMMA ||
//                        childType == ERL_CURLY_LEFT || childType == ERL_CURLY_RIGHT || childType == ERL_RADIX;
//                if (!bracketsCurliesOrComma && childType != ERL_BIN_START && childType != ERL_BIN_END && childType != ERL_LC_EXPRS) return baseAlignment;
//            }
//            if (parentType == ERL_FUN_TYPE_SIGS && childType == ERL_TYPE_SIG) {
//                return baseAlignment;
//            }
//            PsiElement psi = parent.getPsi();
//            if ((psi instanceof ErlangFakeBinaryExpression || parentType == ERL_MAX_EXPRESSION && childType == ERL_STRING_LITERAL) &&
//                    (binaryExpressionIndex > 1 || binaryExpressionIndex < 0)) {
//                return baseAlignment;
//            }
//        }
//        if (myErlangSettings.ALIGN_GUARDS && parentType == ERL_GUARD && childType != ERL_COMMA) return baseAlignment;
        return baseAlignment;
    }
    
    
    @Nullable
    @Override
    public Spacing getSpacing(Block block, Block block1) {
        if (block1 instanceof CMakeFormattingBlock) {
            ASTNode node = ((CMakeFormattingBlock) block1).getNode();
            if (CMakeParserDefinition.COMMENTS.contains(node.getElementType()) && mySettings.KEEP_FIRST_COLUMN_COMMENT) {
                return Spacing.createKeepingFirstColumnSpacing(0, Integer.MAX_VALUE, true, mySettings.KEEP_BLANK_LINES_IN_CODE);
            }
        }
        return null;
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
