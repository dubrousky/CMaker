package cmake.highlights;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Registers highlighter in the plugin.xml
 */
public class CMakeHighlighterFactory extends SyntaxHighlighterFactory {
    /**
     * *
     * @param project
     * @param virtualFile
     * @return
     */
    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
        return new CMakeHighlighter();
    }
}
