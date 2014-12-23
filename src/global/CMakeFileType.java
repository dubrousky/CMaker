package global;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeFileType extends LanguageFileType {
    public static final CMakeFileType INSTANCE = new CMakeFileType();
    private static final String[] DEFAULT_EXTENSIONS = {"txt","cmake"};

    protected CMakeFileType() {
        super(CMakeLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "CMake File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "CMake build system file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSIONS[0];
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return CMakeIcons.FILE;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }
}
