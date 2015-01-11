package cmake.global;

/**
 * Created by alex on 1/1/15.
 */
import com.intellij.lang.CodeDocumentationAwareCommenter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import cmake.psi.CMakeTypes;

public class CMakeCommenter implements CodeDocumentationAwareCommenter {
    public String getLineCommentPrefix() {
        return "#";
    }

    public String getBlockCommentPrefix() {
        return "#[[";
    }

    public String getBlockCommentSuffix() {
        return "]]";
    }

    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    public String getCommentedBlockCommentSuffix() {
        return null;
    }

    @Override
    public IElementType getLineCommentTokenType() {
        
        return CMakeTypes.LINE_COMMENT;
    }

    @Override
    public IElementType getBlockCommentTokenType() {
        return CMakeTypes.BRACKET_COMMENT;
    }

    @Override
    public IElementType getDocumentationCommentTokenType() {
        return null;
    }

    @Override
    public String getDocumentationCommentPrefix() {
        return null;
    }

    @Override
    public String getDocumentationCommentLinePrefix() {
        return null;
    }

    @Override
    public String getDocumentationCommentSuffix() {
        return null;
    }

    @Override
    public boolean isDocumentationComment(PsiComment element) {
        return false;
    }
}

