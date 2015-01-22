package cmake.nav;

import cmake.parsing.CMakeParserUtilImpl;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 1/22/15.
 */
public class CMakeGotoSymbolContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        List<PsiElement> defs = CMakeParserUtilImpl.getDefinedSymbols(project);
        List<String> names = new ArrayList<String>(defs.size());
        for (PsiElement d : defs) {

                names.add(d.getText());
        }
        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean b) {
        List<PsiElement> defs = CMakeParserUtilImpl.getDefinedSymbols(project, name);
        // TODO: Add matching symbols
        return defs.toArray(new NavigationItem[defs.size()]);
    }
}
