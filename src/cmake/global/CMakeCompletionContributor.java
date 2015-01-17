package cmake.global;

import cmake.psi.CMakeArgument;
import cmake.psi.CMakeTypes;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/13/15.
 */
public class CMakeCompletionContributor extends CompletionContributor {
    public CMakeCompletionContributor() {
        extend(CompletionType.SMART,
                PlatformPatterns.psiElement(CMakeTypes.ELSE).withLanguage(CMakeLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"));
                    }
                }
        );
        extend(CompletionType.SMART,
                PlatformPatterns.psiElement(CMakeTypes.IDENTIFIER).withLanguage(CMakeLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("()"));
                    }
                }
        );
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CMakeTypes.QUOTED_ARGUMENT).withLanguage(CMakeLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"));
                    }
                }
        );
    }
}
