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
        if (node.getPsi().getContext() instanceof CMakeCond
                ||node.getPsi().getContext() instanceof CMakePredicateExpr
                ||node.getPsi().getContext() instanceof CMakeCompoundExpr
                ) {
            debug="---->"+debug+"\n";
            
            System.out.print(debug);
            return Indent.getContinuationIndent();
        }
        debug+="\n";
        //System.out.print(debug);
        return Indent.getNoneIndent();
    }

    private static boolean needIndent(@Nullable IElementType type) {
        return type != null;// && CMakeFormattingBlock.BLOCKS_TOKEN_SET.contains(type);
    }
}
