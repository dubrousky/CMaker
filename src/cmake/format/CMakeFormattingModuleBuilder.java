package cmake.format;

import cmake.global.CMakeLanguage;
import cmake.psi.CMakeTypes;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alex on 1/11/15.
 */
public class CMakeFormattingModuleBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {

        CMakeCodeStyleSettings cmakeSettings = settings.getCustomSettings(CMakeCodeStyleSettings.class);
        SpacingBuilder spacingBuilder = createSpacingBuilder(settings, cmakeSettings);
        CMakeFormattingBlock block = new CMakeFormattingBlock(element.getNode(),
                                                                Alignment.createAlignment(),
                                                                Wrap.createWrap(WrapType.NONE,true),
                                                                spacingBuilder
                                                                );
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), block, settings);
    }

    private static SpacingBuilder createSpacingBuilder(@NotNull CodeStyleSettings settings,
                                                       CMakeCodeStyleSettings cmakeSettings) {
        
        //noinspection SuspiciousNameCombination
        return new SpacingBuilder(settings, CMakeLanguage.INSTANCE)
                .after(CMakeTypes.COMPOUND_EXPR).lineBreakInCodeIf(true)
                .after(CMakeTypes.COMMAND_EXPR).lineBreakInCodeIf(true)
                .after(CMakeTypes.PREDICATE_EXPR).lineBreakInCodeIf(true)
                .before(CMakeTypes.SEPARATED_ARGUMENT).spaceIf(true)
                .after(CMakeTypes.COMMAND_NAME).spaceIf(false)
                .after(CMakeTypes.IDENTIFIER).spaceIf(false)
                .after(CMakeTypes.LPAR).spaceIf(true)
                .before(CMakeTypes.RPAR).spaceIf(true)
                .after(CMakeTypes.SEP).spaceIf(true)
                ;
        
    }
    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile psiFile, int i, ASTNode astNode) {
        return null;
    }
}
