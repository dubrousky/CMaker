package cmake.format;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeCodeStyleSettings extends CustomCodeStyleSettings {
    public boolean ALIGN_MULTILINE_BLOCK = false;
    public boolean ALIGN_FUNCTION_CLAUSES = false;
    public int EXPRESSION_IN_CLAUSE_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    protected CMakeCodeStyleSettings(CodeStyleSettings container) {
        super("CMakeCodeStyleSettings", container);
    }
}
