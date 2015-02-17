package cmake.reference;

import cmake.psi.CMakeCommandName;
import cmake.psi.CMakeTypes;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/16/15.
 */
public class CMakeReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CMakeTypes.COMMAND_NAME),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        if(element instanceof CMakeCommandName)
                        {
                            CMakeCommandName literalExpression = (CMakeCommandName) element;
                            String text = (String) literalExpression.getText();
                            if (text != null) {
                                System.out.println("index ->"+text+"\n");
                                return new PsiReference[]{new CMakeReference(element, new TextRange(0, text.length() + 1))};
                            }
                        }
                        return new PsiReference[0];
                    }
                });
//        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CMakeTypes.UNQUOTED_ARGUMENT),
//                new PsiReferenceProvider() {
//                    @NotNull
//                    @Override
//                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
//                        if(element instanceof CMakeUnquotedArgument){
//                            CMakeUnquotedArgument literalExpression = (CMakeUnquotedArgument) element;
//                            String text = (String) literalExpression.getText();
//                            if (text != null) {
//                                System.out.println("index ->"+text+"\n");
//                                return new PsiReference[]{new CMakeReference(element, new TextRange(0, text.length() + 1))};
//                            }
//
//                        }
//                        return new PsiReference[0];
//                    }
//                });
    }
}
