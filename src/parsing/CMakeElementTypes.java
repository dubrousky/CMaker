package parsing;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import parsing.CMakeElementType;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeElementTypes {
    public static final IElementType IF_BEGIN = new CMakeElementType("IF_BEGIN");
    public static final IElementType IF_END = new CMakeElementType("IF_END");
    public static final IElementType FUNCTION_BEGIN = new CMakeElementType("FUNCTION_BEGIN");
    public static final IElementType FUNCTION_END = new CMakeElementType("FUNCTION_BEGIN");
    public static final IElementType FOR_BEGIN = new CMakeElementType("FOR_BEGIN");
    public static final IElementType FOR_END = new CMakeElementType("FOR_END");
    public static final IElementType WHILE_BEGIN = new CMakeElementType("WHILE_BEGIN");
    public static final IElementType WHILE_END = new CMakeElementType("WHILE_END");
    public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType KEYWORD = new CMakeElementType("KEYWORD");
    public static final IElementType STRING = new CMakeElementType("STRING");
    public static final IElementType COMMENT = new CMakeElementType("COMMENT");
    public static final IElementType VAR = new CMakeElementType("VAR");
    public static final IElementType LEFT_BRACE = new CMakeElementType("LEFT_BRACE");
    public static final IElementType RIGHT_BRACE = new CMakeElementType("RIGHT_BRACE");
    public static final TokenSet KEYWORDS    = TokenSet.create(KEYWORD);

    public static final TokenSet COMMENTS    = TokenSet.create(COMMENT);
    public static final TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
    public static final TokenSet STRINGS     = TokenSet.create(STRING);
}
