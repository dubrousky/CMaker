package cmake.global;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by alex on 1/1/15.
 */
public class CMakeFile extends PsiFileBase{
    protected CMakeFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CMakeLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CMakeFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "CMake";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
