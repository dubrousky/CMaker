package cmake.global;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import cmake.psi.CMakeTypes;

/**
 * Created by alex on 12/23/14.
 */
public class CMakeBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(CMakeTypes.LPAR, CMakeTypes.RPAR, true)//,
            //new BracePair(CMakeElementTypes.LEFT_PAR, CMakeElementTypes.RIGHT_PAR, false),
            //new BracePair(CMakeElementTypes.LEFT_ASSOCIATION, CMakeElementTypes.RIGHT_ASSOCIATION, false),
            //new BracePair(CMakeElementTypes.LEFT_BRACKET, CMakeElementTypes.RIGHT_BRACKET, true),
            //new BracePair(CMakeElementTypes.PART_BEGIN, CMakeElementTypes.RIGHT_BRACKET, true)
    };

    /**
     * This functions returns a list of {@link IElementType} pairs which correspond to the opening and closing brace lexer
     * tokens.
     *
     * @return List of the matching brace lexer token pairs.
     */
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}