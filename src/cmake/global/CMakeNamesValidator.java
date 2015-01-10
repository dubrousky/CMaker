package cmake.global;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;

/**
 * Created by alex on 12/30/14.
 */
public class CMakeNamesValidator implements NamesValidator {
    @Override
    public boolean isKeyword(String s, Project project) {
        System.out.println(s);
        return false;
    }

    @Override
    public boolean isIdentifier(String s, Project project) {
        System.out.println(s);
        return false;
    }
}
