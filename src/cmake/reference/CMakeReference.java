package cmake.reference;

import cmake.icons.CMakeIcons;
import cmake.parsing.CMakeParserUtilImpl;
import cmake.psi.CMakeCommandName;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 1/16/15.
 */
public class CMakeReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String name;
    
    public CMakeReference(PsiElement element, TextRange range) {
        super(element, range);
        name = element.getText().substring(range.getStartOffset(), range.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
        Project project = myElement.getProject();
        final List<PsiElement> defs = CMakeParserUtilImpl.getDefinedSymbols(project, name);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (PsiElement d : defs) {
            System.out.println("resolve ->"+d.getText()+"\n");
            results.add(new PsiElementResolveResult(d));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        // Get list of all command definitions plus keywords in project
        List<PsiElement> defs = CMakeParserUtilImpl.getDefinedSymbols(project, name);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        // Populate the variants with resolved elements
        for (final PsiElement d : defs) {
            if (d.getText() != null && d.getText().length() > 0) {
                // command must extend named element
                System.out.println("variants ->"+d.getText()+"\n");
                variants.add(LookupElementBuilder.create((CMakeCommandName) d).
                                withIcon(CMakeIcons.FILE).
                                withTypeText(d.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }
}
