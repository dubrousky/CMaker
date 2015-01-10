package cmake.psi;

import com.intellij.psi.tree.IElementType;
import cmake.global.CMakeLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 12/22/14.
 */
public class CMakeElementType extends IElementType {

    public CMakeElementType(@NotNull @NonNls String debugName) {
        super(debugName, CMakeLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "CMake:" + super.toString();
    }
}