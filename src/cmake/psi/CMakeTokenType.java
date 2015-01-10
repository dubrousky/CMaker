package cmake.psi;

import com.intellij.psi.tree.IElementType;
import cmake.global.CMakeLanguage;

/**
 * Created by alex on 1/4/15.
 */
public class CMakeTokenType extends IElementType {

    public CMakeTokenType(String debugName) {
        super(debugName, CMakeLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "CMakeTokenType." + super.toString();
    }
}
