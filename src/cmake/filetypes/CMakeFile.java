package cmake.filetypes;

import cmake.global.CMakeLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * CMake language file type used as root of the 
 * cmake psi file elements holder.
 */
public class CMakeFile extends PsiFileBase{
    public static CMakeFile create(@NotNull FileViewProvider viewProvider) {
        return new CMakeFile(viewProvider);
    }
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
