package cmake.format;

import com.intellij.lang.ASTNode;
import com.intellij.formatting.Indent;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeIndentProcessor {
    private final CMakeCodeStyleSettings myCMakeSettings;

    public CMakeIndentProcessor(@NotNull CMakeCodeStyleSettings cmakeSettings) {
        myCMakeSettings = cmakeSettings;
    }

    public Indent getChildIndent(ASTNode node, int binaryExpressionIndex) {
        if (binaryExpressionIndex > 0) {
            return Indent.getNormalIndent();
        }
        return null;
    }

    private static boolean needIndent(@Nullable IElementType type) {
        return type != null && CMakeFormattingBlock.BLOCKS_TOKEN_SET.contains(type);
    }
}
