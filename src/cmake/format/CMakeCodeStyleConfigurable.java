package cmake.format;

import cmake.global.CMakeLanguage;
import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/13/15.
 */
public class CMakeCodeStyleConfigurable extends CodeStyleAbstractConfigurable {
    public CMakeCodeStyleConfigurable(CodeStyleSettings settings, CodeStyleSettings cloneSettings) {
        super(settings, cloneSettings, "CMake");
    }

    @Override
    protected CodeStyleAbstractPanel createPanel(CodeStyleSettings codeStyleSettings) {
        return new CMakeCodeStylePanel(getCurrentSettings(), codeStyleSettings);
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
    
    class CMakeCodeStylePanel extends TabbedLanguageCodeStylePanel {
        protected CMakeCodeStylePanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(CMakeLanguage.INSTANCE, currentSettings, settings);
        }
    }
}
