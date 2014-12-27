package global;

/**
 * Created by alex on 12/27/14.
 */
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.TokenType;
import parsing.CMakeElementType;
import parsing.CMakeElementTypes;

public class CMakeQuoteHandler extends SimpleTokenSetQuoteHandler {
    public CMakeQuoteHandler() {
        super(CMakeElementTypes.STRING, TokenType.BAD_CHARACTER);
    }

    @Override
    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
        return true;
    }
}
