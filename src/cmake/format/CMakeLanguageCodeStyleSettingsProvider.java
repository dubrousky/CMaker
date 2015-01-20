package cmake.format;

import cmake.global.CMakeLanguage;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/13/15.
 */
public class CMakeLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return CMakeLanguage.INSTANCE;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        if (settingsType == SettingsType.SPACING_SETTINGS || settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            return "function ( eblia )\n" +
                    "\t#jkghfdkgjdkgdgjdf\n" +
                    "\t#[[njfkjgjergergjrghrekgherjkg]]\n" +
                    "\treturn()\n"+
                    "endfunction( )\n" +
                    "macro( install_example name )\n" +
                    "\tmessage (${my_message} )\n" +
                    "endmacro( )\n" +
                    "if( \"nknkkn\" \"jbjh\" )\n" +
                    "\tmessage(${in_if_clause})\n"+
                    "endif( )\n" +
                    "\n" +
                    "if( h )\n" +
                    "\tmessage(${in_if_clause})\n"+
                    "elseif( h )\n" +
                    "\tmessage(${in_elseif_clause})\n"+
                    "endif( ${d} )";
        }
        if (settingsType == SettingsType.INDENT_SETTINGS) {
            return "function ( eblia )\n" +
                    "\t#jkghfdkgjdkgdgjdf\n" +
                    "\t#[[njfkjgjergergjrghrekgherjkg]]\n" +
                    "\treturn()\n"+
                    "endfunction( )\n" +
                    "macro( install_example name )\n" +
                    "\tmessage (${my_message} )\n" +
                    "endmacro( )\n" +
                    "if( \"nknkkn\" \"jbjh\" )\n" +
                    "\tmessage(${in_if_clause})\n"+
                    "endif( )\n" +
                    "\n" +
                    "if( h )\n" +
                    "\tmessage(${in_if_clause})\n"+
                    "elseif( h )\n" +
                    "\tmessage(${in_elseif_clause})\n"+
                    "endif( ${d} )";
        }
        return "TODO";
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings defaultSettings = new CommonCodeStyleSettings(getLanguage());
        CommonCodeStyleSettings.IndentOptions indentOptions = defaultSettings.initIndentOptions();
        indentOptions.INDENT_SIZE = 2;
        indentOptions.CONTINUATION_INDENT_SIZE = 4;
        indentOptions.TAB_SIZE = 2;

        return defaultSettings;
    }
}
