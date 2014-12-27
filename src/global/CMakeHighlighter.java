package global;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import parsing.CMakeElementTypes;
import parsing.CMakeLexer;

import java.util.Map;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeHighlighter extends SyntaxHighlighterBase {
    private static final Map<IElementType, TextAttributesKey> keys1;
    private static final Map<IElementType, TextAttributesKey> keys2;
    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CMakeLexer();
    }

    // TODO: Add text highlighting attributes
    // TODO: Add mapping between token and its highlighting properties

    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(
        "CMAKE.KEY",
        DefaultLanguageHighlighterColors.KEYWORD
      );

    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey(
            "CMAKE.LINE_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
    );

    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(
            "CMAKE.STRING",
            DefaultLanguageHighlighterColors.STRING
    );

    public static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey(
            "CMAKE.BRACES",
            DefaultLanguageHighlighterColors.BRACES
    );

    public static final TextAttributesKey BADCHAR = TextAttributesKey.createTextAttributesKey(
            "CMAKE.BADCHAR",
            DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE
    );

    public static final TextAttributesKey VAREXP = TextAttributesKey.createTextAttributesKey(
            "CMAKE.VAREXP",
            DefaultLanguageHighlighterColors.IDENTIFIER
    );

    public static final TextAttributesKey ESCAPED_CHAR = TextAttributesKey.createTextAttributesKey(
            "CMAKE.ESCAPED_CHAR",
            DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE
    );

    public static final TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(
            "CMAKE.BLOCK_COMMENT",
            DefaultLanguageHighlighterColors.BLOCK_COMMENT
    );

    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(
            "CMAKE.NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
    );

    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "CMAKE.NUMBER",
            DefaultLanguageHighlighterColors.IDENTIFIER
    );
    /*
    public static final TextAttributesKey PROPERTY_VALUE = TextAttributesKey.createTextAttributesKey(
      "PROPERTIES.VALUE",
      DefaultLanguageHighlighterColors.STRING
    );


    public static final TextAttributesKey PROPERTY_KEY_VALUE_SEPARATOR = TextAttributesKey.createTextAttributesKey(
      "PROPERTIES.KEY_VALUE_SEPARATOR",
      DefaultLanguageHighlighterColors.OPERATION_SIGN
    );
    public static final TextAttributesKey PROPERTIES_VALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
      "PROPERTIES.VALID_STRING_ESCAPE",
      DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE
    );
    public static final TextAttributesKey PROPERTIES_INVALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
      "PROPERTIES.INVALID_STRING_ESCAPE",
      DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE
    );
      */
    static {
        keys1 = new THashMap<IElementType, TextAttributesKey>();
        keys2 = new THashMap<IElementType, TextAttributesKey>();
        // TODO: Populate maps here
        keys1.put(CMakeElementTypes.KEYWORD, KEYWORD);
        keys1.put(CMakeElementTypes.COMMENT, COMMENT);
        keys1.put(CMakeElementTypes.BLOCK_COMMENT, COMMENT);
        keys1.put(CMakeElementTypes.STRING, STRING);
        keys1.put(CMakeElementTypes.LEFT_BRACE, BRACES);
        keys1.put(CMakeElementTypes.RIGHT_BRACE, BRACES);
        keys1.put(CMakeElementTypes.BAD_CHARACTER, BADCHAR);
        keys1.put(CMakeElementTypes.VAR, VAREXP);
        keys1.put(CMakeElementTypes.ESCAPED_CHAR,ESCAPED_CHAR);
        keys1.put(CMakeElementTypes.BLOCK_COMMENT,BLOCK_COMMENT);
        keys1.put(CMakeElementTypes.NUMBER,NUMBER);
        keys1.put(CMakeElementTypes.IDENTIFIER,IDENTIFIER);
    }
    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        return SyntaxHighlighterBase.pack(keys1.get(iElementType), keys2.get(iElementType));
    }
    //TODO: Fill the map to use it in the ColorsPage
    public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES = new THashMap<TextAttributesKey, Pair<String, HighlightSeverity>>(6);

}
