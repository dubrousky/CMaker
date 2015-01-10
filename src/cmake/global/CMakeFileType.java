package cmake.global;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.encoding.EncodingRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.charset.Charset;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeFileType extends LanguageFileType {
    public static final CMakeFileType INSTANCE = new CMakeFileType();
    private static final String[] DEFAULT_EXTENSIONS = {"cmake","txt"};

    protected CMakeFileType() {
        super(CMakeLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "CMake";
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
        Charset charset = EncodingRegistry.getInstance().getDefaultCharsetForPropertiesFiles(virtualFile);
        if (charset == null) {
            charset = CharsetToolkit.getDefaultSystemCharset();
        }
        return charset.name();
    }
}
