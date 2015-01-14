package cmake.global;

import cmake.psi.*;
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
import com.intellij.util.PairProcessor;
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
                return true;
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
            if(myElement instanceof CMakeCompoundExpr)
                return myElement.getFirstChild().getText();
            else if(myElement instanceof CMakeFile)
                return myElement.getContainingFile().getName();
            else
                return null;
        }

        @Nullable
        @Override
        public String getLocationString() {
            return "";
        }

        @Nullable
        @Override
        public Icon getIcon(boolean b) {
            if(myElement instanceof CMakeFile)
                return CMakeIcons.FILE;
            else if(myElement instanceof CMakeCompoundExpr)
                return CMakeIcons.FUN;
            else
                return null;
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
            return this;
            //return myElement instanceof NavigationItem ?
            //        ((NavigationItem) myElement).getPresentation() : this;
        }

        @NotNull
        @Override
        public TreeElement[] getChildren() {
            if (myElement instanceof CMakeFile) {
                final List<TreeElement> treeElements = new ArrayList<TreeElement>();
                CMakeFileElement[] elements = PsiTreeUtil.getChildrenOfType(myElement, CMakeFileElement.class);
                for( CMakeFileElement e : elements )
                {
                    if(e.getFirstChild() instanceof CMakeComposite
                            && null != ((CMakeComposite) e.getFirstChild()).getBlock())
                    {
                        treeElements.add(new CMakeViewElement(((CMakeComposite) e.getFirstChild()).getBlock().getCompoundExpr()));
                        
                    }
                }

                return treeElements.toArray(new TreeElement[treeElements.size()]);
            } else {
                return EMPTY_ARRAY;
            }
        }
    }
}
