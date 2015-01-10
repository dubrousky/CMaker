package cmake.global;

/**
 * Created by alex on 1/11/15.
 */

import cmake.psi.CMakeTypes;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.TokenType;

/**
 * Created by alex on 12/27/14.
 */
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.TokenType;
import cmake.psi.CMakeTypes;

public class CMakeBCommentHandler extends SimpleTokenSetQuoteHandler {
    public CMakeBCommentHandler() {
        super(CMakeTypes.BRACKET_COMMENT, TokenType.BAD_CHARACTER);
    }

    @Override
    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
        return true;
    }
}
