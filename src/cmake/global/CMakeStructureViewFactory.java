package cmake.global;

import cmake.psi.CMakeBlock;
import cmake.psi.CMakeCommandInvocation;
import cmake.psi.CMakeFileElement;
import cmake.psi.CMakeLoop;
import com.intellij.ide.structureView.*;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.RowIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
                return new CMakeViewModel(psiFile);
            }

            @Override
            public boolean isRootNodeShown() {
                return false;
            }
        };
    }

    public static class CMakeViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
        public CMakeViewModel(@NotNull final PsiFile psiFile) {
            super(psiFile, new CMakeViewElement(psiFile));
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

    public static class CMakeViewElement implements StructureViewTreeElement, ItemPresentation, NavigationItem {
        private final PsiElement myElement;

        public CMakeViewElement(PsiElement element) {
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
            return "";
        }

        @Nullable
        @Override
        public Icon getIcon(boolean b) {
            return CMakeIcons.FILE;
        }

        @Nullable
        @Override
        public String getName() {
            return myElement.getText();
        }

        @Override
        public void navigate(boolean b) {
            if (myElement instanceof NavigationItem) {
                ((NavigationItem) myElement).navigate(b);
            }
        }

        @Override
        public boolean canNavigate() {
            return myElement instanceof NavigationItem &&
                    ((NavigationItem)myElement).canNavigate();
        }

        @Override
        public boolean canNavigateToSource() {
            return myElement instanceof NavigationItem &&
                    ((NavigationItem)myElement).canNavigateToSource();
        }

        @NotNull
        @Override
        public ItemPresentation getPresentation() {
            return myElement instanceof NavigationItem ?
                    ((NavigationItem) myElement).getPresentation() : null;
        }

        @NotNull
        @Override
        public TreeElement[] getChildren() {
            if (myElement instanceof CMakeFile) {
                CMakeFileElement[] elements = PsiTreeUtil.getChildrenOfType(myElement, CMakeFileElement.class);
                List<TreeElement> treeElements = new ArrayList<TreeElement>(elements.length);
                for ( CMakeFileElement f : elements ) {
                    treeElements.add(new CMakeViewElement(f));
                }
                return treeElements.toArray(new TreeElement[treeElements.size()]);
            } else {
                return EMPTY_ARRAY;
            }
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
