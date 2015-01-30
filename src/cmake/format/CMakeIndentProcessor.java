package cmake.format;

import cmake.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.formatting.Indent;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeIndentProcessor {

    public CMakeIndentProcessor() {
        
    }

    public Indent getChildIndent(ASTNode node) {
        String debug = "<"+node.getText()+">\n";
        if (null != node
            &&node.getPsi() instanceof CMakeFileElement
            &&null != PsiTreeUtil.findFirstParent(node.getPsi(), new Condition<PsiElement>() {
                    @Override
                    public boolean value(PsiElement psiElement) {
                        return psiElement instanceof CMakeBody;
                    }
            }))
        {
            System.out.print(debug);
            return Indent.getContinuationIndent(false);
        }
        return Indent.getNoneIndent();
    }
}
