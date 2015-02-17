package cmake.indexing;

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.AbstractStubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/28/15.
 */
public class CMakeStubIndex extends AbstractStubIndex<String,PsiElement> {
    @NotNull
    @Override
    public StubIndexKey<String, PsiElement> getKey() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return null;
    }
}
