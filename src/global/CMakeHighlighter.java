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
    }
    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        return SyntaxHighlighterBase.pack(keys1.get(iElementType), keys2.get(iElementType));
    }

    public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES = new THashMap<TextAttributesKey, Pair<String, HighlightSeverity>>(6);

}
