package cmake.filetypes;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * Registers the mapping between the extension and
 * and cmake file extension.
 */
public class CMakeFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(CMakeFileType.INSTANCE,"cmake");
    }
}
