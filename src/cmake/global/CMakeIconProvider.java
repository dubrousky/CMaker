package cmake.global;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by alex on 1/1/15.
 */
public class CMakeIconProvider extends IconProvider implements DumbAware {


    @Nullable
    @Override
    public Icon getIcon(PsiElement psiElement, int i) {
        if( psiElement instanceof CMakeFile )
            return CMakeIcons.FILE;
        return null;
    }
}
