package cmake.global;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

/**
 * Created by alex on 1/13/15.
 */
public class CMakeAnnotator implements Annotator {
    @Override
    public void annotate(PsiElement psiElement, AnnotationHolder annotationHolder) {
//        // TODO: check if psiElement of required type
//        if (element instanceof PsiLiteralExpression) {
//            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
//            // TODO: check value of element
//            String value = (String) literalExpression.getValue();
//            if (value != null && value.startsWith("simple:")) {
//                // TODO: get project, extract file elements
//                Project project = element.getProject();
//                List<SimpleProperty> properties = SimpleUtil.findProperties(project, value.substring(7));
//                // TODO: create annotation at text range
//                if (properties.size() == 1) {
//                    TextRange range = new TextRange(psiElement.getTextRange().getStartOffset() + 7,
//                            psiElement.getTextRange().getStartOffset() + 7);
//                    Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
//                    annotation.setTextAttributes(SyntaxHighlighterColors.LINE_COMMENT);
//                } else if (properties.size() == 0) {
//                    TextRange range = new TextRange(psiElement.getTextRange().getStartOffset() + 8,
//                            psiElement.getTextRange().getEndOffset());
//                    holder.createErrorAnnotation(range, "Unresolved property");
//                }
//            }
//        }
    }
}
