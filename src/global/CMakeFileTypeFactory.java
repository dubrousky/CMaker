package global;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(new CMakeFileType());
    }
}
