package cmake.global;

import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/22/15.
 */
public class CMakeElementDescriptionProvider implements ElementDescriptionProvider {
    @Nullable
    @Override
    public String getElementDescription(PsiElement psiElement, ElementDescriptionLocation elementDescriptionLocation) {
        return psiElement.getText();
    }
}
