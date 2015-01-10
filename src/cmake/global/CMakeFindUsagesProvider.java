package cmake.global;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.WordOccurrence;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementDescriptionUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.usageView.UsageViewLongNameLocation;
import com.intellij.usageView.UsageViewTypeLocation;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import cmake.psi.CMakeTypes;
import cmake.parsing.CMakeLexer;

/**
 * Created by alex on 12/30/14.
 */
public class CMakeFindUsagesProvider implements FindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new WordsScanner() {
            @Override
            public void processWords(CharSequence charSequence, Processor<WordOccurrence> processor) {
                CMakeLexer lexer = new CMakeLexer();
                lexer.start(charSequence);
                IElementType tokenType;
                while ((tokenType = lexer.getTokenType()) != null) {
                    //TODO process occurrences in string literals and comments
                    if (tokenType == CMakeTypes.VAR_REF) {
                        int tokenStart = lexer.getTokenStart();
                        for (TextRange wordRange : StringUtil.getWordIndicesIn(lexer.getTokenText())) {
                            int start = tokenStart + wordRange.getStartOffset();
                            int end = tokenStart + wordRange.getEndOffset();
                            processor.process(new WordOccurrence(charSequence, start, end, WordOccurrence.Kind.CODE));
                        }
                    }
                    lexer.advance();
                }
            }
        };
    }

    @Override
    public boolean canFindUsagesFor(PsiElement psiElement) {
        return false;
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
        return ElementDescriptionUtil.getElementDescription(psiElement, UsageViewLongNameLocation.INSTANCE);
    }
}
