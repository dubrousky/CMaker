package cmake.format;

import cmake.global.CMakeLanguage;
import cmake.psi.CMakeTypes;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.SpacingBuilder;
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
        CommonCodeStyleSettings commonSettings = settings.getCommonSettings(CMakeLanguage.INSTANCE);
        CMakeCodeStyleSettings cmakeSettings = settings.getCustomSettings(CMakeCodeStyleSettings.class);
        SpacingBuilder spacingBuilder = createSpacingBuilder(commonSettings, cmakeSettings);
        CMakeFormattingBlock block = new CMakeFormattingBlock(element.getNode(), 
                                                                null,
                                                                null,
                                                                null,
                                                                commonSettings,
                                                                cmakeSettings,
                                                                spacingBuilder,
                                                                -1);
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), block, settings);
    }

    private static SpacingBuilder createSpacingBuilder(@NotNull CommonCodeStyleSettings settings,
                                                       CMakeCodeStyleSettings cmakeSettings) {
        
        //noinspection SuspiciousNameCombination
        return new SpacingBuilder(settings.getRootSettings(), CMakeLanguage.INSTANCE)
                .after(CMakeTypes.ARGUMENT).spaceIf(true)
                .after(CMakeTypes.PREDICATE_EXPR).lineBreakInCodeIf(true)
                .after(CMakeTypes.COMMAND_EXPR).lineBreakInCodeIf(true)
                .after(CMakeTypes.COMMAND_NAME).spaceIf(true)
                .after(CMakeTypes.KEYWORD).spaceIf(true)
                ;
        
    }
    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile psiFile, int i, ASTNode astNode) {
        return null;
    }
}
