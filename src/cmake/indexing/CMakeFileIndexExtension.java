package cmake.indexing;

import cmake.filetypes.CMakeFile;
import cmake.filetypes.CMakeFileType;
import cmake.parsing.CMakeParserUtilImpl;
import cmake.psi.CMakeArguments;
import cmake.psi.CMakeCommandExpr;
import cmake.psi.impl.CMakeCommandExprImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.Consumer;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 Each specific index implementation is a class extending FileBasedIndexExtension,
 registered in the <fileBasedIndex> extension point. 
 The implementation contains of the following main parts:

 getIndexer() returns the indexer class, which is responsible for actually building a 
                set of key/value pairs based on the file content.
 getKeyDescriptor() returns the key descriptor, which is responsible for comparing the
                keys and storing them in a serialized binary format. Probably the most 
                commonly used KeyDescriptor implementation is EnumeratorStringDescriptor 
                implementation, designed for storing identifiers in an efficient way.
 getValueExternalizer() returns the value serializer, which takes care of storing values
                in a serialized binary format.
 getInputFilter() allows to restrict the indexing only to a certain set of files.
 getVersion() returns the version of the index implementation. The index is automatically 
                rebuilt if the current version differs from the version of the index 
                implementation used to build the index.
 */
public class CMakeFileIndexExtension extends FileBasedIndexExtension<String, CMakeCommandExpr> {
    private static final ID<String, CMakeCommandExpr> CMAKE_APPLICATION_INDEX = ID.create("CMakeApplicationIndex");
    @NotNull
    @Override
    public ID<String, CMakeCommandExpr> getName() {
        return CMAKE_APPLICATION_INDEX;
    }

    @NotNull
    @Override
    public DataIndexer<String, CMakeCommandExpr, FileContent> getIndexer() {
        return new DataIndexer<String, CMakeCommandExpr, FileContent>() {
            @NotNull
            @Override
            public Map<String, CMakeCommandExpr> map(@NotNull FileContent fileContent) {
                Map<String, CMakeCommandExpr> res = new THashMap<String, CMakeCommandExpr>();
                // Create psi file from content and use visitor to pick up values
//                if(null != fileContent)
//                {
//                    PsiFile file =  fileContent.getPsiFile();
//                    if( file instanceof CMakeFile) {
//                        List<PsiElement> defs = CMakeParserUtilImpl.getDefinedSymbols(fileContent.getPsiFile());
//                        for (PsiElement d : defs) {
//                            res.put(((CMakeCommandExpr) d).getArguments().getArgument().getText(), (CMakeCommandExpr) d);
//                        }
//                    }
//                }
                return res;
            }
        };
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return new EnumeratorStringDescriptor();
    }

    @NotNull
    @Override
    public DataExternalizer<CMakeCommandExpr> getValueExternalizer() {
        return new DataExternalizer<CMakeCommandExpr>() {
            @Override
            public void save(@NotNull DataOutput dataOutput, CMakeCommandExpr cMakeCommandExpr) throws IOException {
                dataOutput.writeChars(cMakeCommandExpr.getText());
            }

            @Override
            public CMakeCommandExpr read(@NotNull DataInput dataInput) throws IOException {
                // Create new file from string content and return first file element
                // TODO:
                String name = "test.dummy.cmake";
                CMakeFile tmp = (CMakeFile) PsiFileFactory.getInstance(null).
                        createFileFromText(name, CMakeFileType.INSTANCE, dataInput.readLine());
                return (CMakeCommandExpr) tmp.getFirstChild().getFirstChild().getFirstChild();
            }
        };
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new FileBasedIndex.InputFilter() {
            @Override
            public boolean acceptInput(@NotNull VirtualFile virtualFile) {
                return virtualFile.getFileType() instanceof CMakeFileType;
            }
        };
    }

    @Override
    public boolean dependsOnFileContent() {
        return false;
    }

    @Override
    public int getVersion() {
        return 0;
    }
}
