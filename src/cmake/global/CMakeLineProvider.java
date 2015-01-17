package cmake.global;

import cmake.icons.CMakeIcons;
import cmake.psi.CMakeFbegin;
import cmake.psi.CMakeLoop;
import cmake.psi.CMakeMbegin;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Provides gutter icons for function/macro/loop
 * elements.
 */
public class CMakeLineProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof CMakeFbegin) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(CMakeIcons.FUN).
                            setTargets(element).
                            setTooltipText("Navigate to a simple property");
            result.add(builder.createLineMarkerInfo(element));

        }

        if (element instanceof CMakeMbegin) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(CMakeIcons.MACRO).
                            setTargets(element).
                            setTooltipText("Navigate to a simple property");
            result.add(builder.createLineMarkerInfo(element));

        }
        
        else if (element instanceof CMakeLoop) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(CMakeIcons.LOOP).
                            setTargets(element).
                            setTooltipText("Navigate to a simple property");
            result.add(builder.createLineMarkerInfo(element));

        }
    }
}
