package cmake.global;

import cmake.psi.CMakeBlock;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Created by alex on 1/14/15.
 */
public class CMakeLineProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof CMakeBlock) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(CMakeIcons.FILE).
                            setTargets(element).
                            setTooltipText("Navigate to a simple property");
            result.add(builder.createLineMarkerInfo(element));

        }
    }
}