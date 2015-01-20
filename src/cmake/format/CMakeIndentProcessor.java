package cmake.format;

import cmake.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.formatting.Indent;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeIndentProcessor {

    public CMakeIndentProcessor() {
        
    }

    public Indent getChildIndent(ASTNode node) {
        String debug = "<"+node.getText()+">";
        if (
                null != node
                && null != node.getTreeParent()
                && node.getTreeParent().getPsi() instanceof CMakeBody
            ) 
        {
            System.out.print(debug);
            return Indent.getNormalIndent(false);
        }
        return Indent.getNoneIndent();
    }

    private static boolean needIndent(@Nullable IElementType type) {
        return type != null && CMakeFormattingBlock.BLOCKS_TOKEN_SET.contains(type);
    }
}
