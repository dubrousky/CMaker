package cmake.global;

import cmake.filetypes.CMakeFile;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import cmake.psi.CMakeTypes;

import java.util.List;

/**
 * Created by alex on 12/31/14.
 */
public class CMakeFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof CMakeFile)) return FoldingDescriptor.EMPTY;
        CMakeFile file = (CMakeFile) root;
        
        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();
        /*for (ErlangFunction function : file.getFunctions()) {
            result.add(new FoldingDescriptor(function, function.getTextRange()));
        }*/

        if (!quick) {
            // Add condition to check if tokens pair
            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element) {
                    if (TokenSet.create(CMakeTypes.LINE_COMMENT,
                            CMakeTypes.BRACKET_COMMENT,
                            CMakeTypes.COMPOUND_EXPR,
                            CMakeTypes.PREDICATE_EXPR
                    ).contains(element.getNode().getElementType()) && element.getTextRange().getLength() > 2) {
                        result.add(new FoldingDescriptor(element, element.getTextRange()));
                    }
                    /*else if(element.getNode().getElementType() == CMakeTypes.ENDIF_EXPR)
                    {
                        // find previous expr and fold it together with endif
                        PsiElement prev = element.getPrevSibling();
                        while(prev.getNode().getElementType() != CMakeTypes.PREDICATE_EXPR)
                        {
                            prev = prev.getPrevSibling();
                        }
                        TextRange r = new TextRange(prev.getTextOffset(),
                                element.getTextOffset()+element.getTextLength());
                        result.add(new FoldingDescriptor(prev,r));
                    }*/
                    return true;
                }
            });
        }

        return result.toArray(new FoldingDescriptor[result.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        //if (cmake.psi instanceof ErlangFunction) return ErlangPsiImplUtil.createFunctionPresentation((ErlangFunction) cmake.psi) + " -> ...";
        IElementType type = node.getElementType();
        if (CMakeTypes.BRACKET_COMMENT == type) return "[[ ... ]]";
        if (CMakeTypes.COMPOUND_EXPR  == type)
        {
            return node.getFirstChildNode().getText() + " ...";
        }
        if (CMakeTypes.PREDICATE_EXPR == type)
        {
            return node.getFirstChildNode().getText()+" ...";
        }
        if (CMakeTypes.COND == type)
        {
            return node.getFirstChildNode().getText()+" ...";
        }
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}