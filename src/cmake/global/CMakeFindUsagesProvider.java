package cmake.global;

import cmake.psi.CMakeCommandName;
import cmake.psi.CMakeNamedElement;
import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordOccurrence;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementDescriptionUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.usageView.UsageViewLongNameLocation;
import com.intellij.usageView.UsageViewNodeTextLocation;
import com.intellij.usageView.UsageViewTypeLocation;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import cmake.psi.CMakeTypes;
import cmake.parsing.CMakeLexerAdapter;

/**
 * Created by alex on 12/30/14.
 */
public class CMakeFindUsagesProvider implements FindUsagesProvider {
    private static final DefaultWordsScanner WORDS_SCANNER =
            new DefaultWordsScanner(new CMakeLexerAdapter(),
                    TokenSet.create(CMakeTypes.COMMAND_NAME),
                    TokenSet.create(CMakeTypes.LINE_COMMENT,
                                    CMakeTypes.BRACKET_COMMENT),
                    TokenSet.EMPTY);
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return WORDS_SCANNER;
    }

    @Override
    public boolean canFindUsagesFor(PsiElement psiElement) {
        return true;/*psiElement instanceof CMakeNamedElement;*/
    }

    @Nullable
    @Override
    public String getHelpId(PsiElement psiElement) {
        return HelpID.FIND_OTHER_USAGES;
    }

    @NotNull
    @Override
    public String getType(PsiElement psiElement) {
        return ElementDescriptionUtil.getElementDescription(psiElement, UsageViewTypeLocation.INSTANCE);
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement psiElement) {
        return ElementDescriptionUtil.getElementDescription(psiElement, UsageViewLongNameLocation.INSTANCE);
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement psiElement, boolean b) {
        return ElementDescriptionUtil.getElementDescription(psiElement, UsageViewNodeTextLocation.INSTANCE);
    }
}
