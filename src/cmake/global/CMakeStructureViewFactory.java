package cmake.global;

import cmake.psi.CMakeCommandInvocation;
import com.intellij.ide.structureView.*;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.ui.RowIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;

/**
 * Created by alex on 1/5/15.
 */
public class CMakeStructureViewFactory implements PsiStructureViewFactory {
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            @NotNull
            @Override
            public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
                return new Model(psiFile);
            }

            @Override
            public boolean isRootNodeShown() {
                return false;
            }
        };
    }

    public static class Model extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
        public Model(@NotNull final PsiFile psiFile) {
            super(psiFile, new Element(psiFile));
            withSuitableClasses(CMakeFile.class, CMakeCommandInvocation.class);
        }

        @Override
        public boolean isAlwaysShowsPlus(StructureViewTreeElement structureViewTreeElement) {
            return false;
        }

        @Override
        public boolean isAlwaysLeaf(StructureViewTreeElement structureViewTreeElement) {
            return false;
        }
    }

    public static class Element implements StructureViewTreeElement, ItemPresentation, NavigationItem {
        private final PsiElement myElement;

        public Element(PsiElement element) {
            this.myElement = element;
        }

        @Override
        public Object getValue() {
            return myElement;
        }

        @Nullable
        @Override
        public String getPresentableText() {
            return "CMake Element";
        }

        @Nullable
        @Override
        public String getLocationString() {
            return null;
        }

        @Nullable
        @Override
        public Icon getIcon(boolean b) {
            return null;
        }

        @Nullable
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void navigate(boolean b) {

        }

        @Override
        public boolean canNavigate() {
            return false;
        }

        @Override
        public boolean canNavigateToSource() {
            return false;
        }

        @NotNull
        @Override
        public ItemPresentation getPresentation() {
            return null;
        }

        @NotNull
        @Override
        public TreeElement[] getChildren() {
            return new TreeElement[0];
        }
    }
    
    @NotNull
    private static Icon createRowIcon(Icon first, Icon second) {
        RowIcon rowIcon = new RowIcon(2);
        rowIcon.setIcon(first, 0);
        rowIcon.setIcon(second, 1);
        return rowIcon;
    }
}
