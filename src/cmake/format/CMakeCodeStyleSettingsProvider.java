package cmake.format;

import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/13/15.
 */
public class CMakeCodeStyleSettingsProvider extends CodeStyleSettingsProvider {
    @Override
    public String getConfigurableDisplayName() {
        return "CMake";
    }
    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings codeStyleSettings, CodeStyleSettings codeStyleSettings1) {
        return new CMakeCodeStyleConfigurable(codeStyleSettings, codeStyleSettings1);
    }

    @NotNull
    @Override
    public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
        return new CMakeCodeStyleSettings(settings);
    }
}
