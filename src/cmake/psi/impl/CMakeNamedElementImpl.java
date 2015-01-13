package cmake.psi.impl;

import cmake.psi.CMakeNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/14/15.
 */
public abstract class CMakeNamedElementImpl extends ASTWrapperPsiElement implements CMakeNamedElement {
    public CMakeNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
