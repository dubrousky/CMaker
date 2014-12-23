package parsing;

import com.intellij.psi.tree.IElementType;
import global.CMakeLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 12/22/14.
 */
public class CMakeElementType extends IElementType {

    public CMakeElementType(@NotNull @NonNls String debugName) {
        super(debugName, CMakeLanguage.INSTANCE);
    }


}