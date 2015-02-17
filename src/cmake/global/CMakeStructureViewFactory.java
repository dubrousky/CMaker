package cmake.global;

import cmake.filetypes.CMakeFile;
import cmake.icons.CMakeIcons;
import cmake.parsing.CMakeParserUtilImpl;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class builds the presentation of the cmake file
 * that contains function/macro definitions, variable
 * bindings and libraries/targets built.
 * Registered at plugin.xml
 */
public class CMakeStructureViewFactory implements PsiStructureViewFactory {
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            /**
             * @doc Method creates representation for the CMake file 
             * @param editor
             * @return Structure view model that represents CMake file
             */
            @NotNull
            @Override
            public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
                return new CMakeViewModel(psiFile);
            }

            /**
             * Determines if to show root node. Show the the CMake root node
             * @return
             */
            @Override
            public boolean isRootNodeShown() {
                return true;
            }
        };
    }

    /**
     * Class represents the model for CMake files
     */
    public static class CMakeViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
        public CMakeViewModel(@NotNull final PsiFile psiFile) {
            super(psiFile, new CMakeViewElement(psiFile));
            withSuitableClasses(CMakeFile.class);
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

    /**
     * Class represents the item of the CMake file structure model.
     * Created from the PsiElement of the tree. This is wrapper around
     * the element with the presentation(icon,location,text) and navigation functionality
     */
    public static class CMakeViewElement implements StructureViewTreeElement, ItemPresentation, NavigationItem {
        private final PsiElement myElement;

        public CMakeViewElement(PsiElement element) {
            this.myElement = element;
        }
        
        @Override
        public Object getValue() {
            return myElement;
        }

        // ItemPresentation part
        @Nullable
        @Override
        public String getPresentableText() {
            /*
            if(myElement instanceof CMakeCommandExpr)
                ((CMakeCommandExpr) myElement).getPresentation().getPresentableText();
            return null;
            */
            if(myElement instanceof CMakeCommandExpr) {
                CMakeArguments args = ((CMakeCommandExpr) myElement).getArguments();
                StringBuilder stringBuilder = new StringBuilder();
                if(args.getTextLength()>0) {
                    stringBuilder.append(args.getArgument().getText())
                            .append("(");
                    for (PsiElement a : args.getSeparatedArgumentList()) {
                        stringBuilder.append(" ");
                        stringBuilder.append(a.getText());
                    }
                    stringBuilder.append(" ) : ")
                            .append(myElement.getFirstChild().getText());
                }
                return  stringBuilder.toString();
            }
            else if(myElement instanceof CMakeFile)
                return myElement.getContainingFile().getName();
            else
                return "";
        }

        @Nullable
        @Override
        public String getLocationString() {
            /*if(myElement instanceof CMakeCommandExpr)
                ((CMakeCommandExpr) myElement).getPresentation().getLocationString();
            return null;
            */
            return null!=myElement.getContainingFile()?myElement.getContainingFile().getName():null;
        }

        @Nullable
        @Override
        public Icon getIcon(boolean b) {
            if(myElement instanceof CMakeFile)
                return CMakeIcons.FILE;
            else if(myElement instanceof CMakeCommandExpr) {
                if (myElement.getFirstChild().getNode().getElementType() == CMakeTypes.FUNCTION)
                    return CMakeIcons.FUN;
                else if (myElement.getFirstChild().getNode().getElementType() == CMakeTypes.MACRO)
                    return CMakeIcons.MACRO;
                else
                    return null;
            }
            else
                return null;
        }

        @Nullable
        @Override
        public String getName() {
            return myElement.getText();
        }
        // Navigation part
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
            //        ((NavigationItem) myElement).getPresentation() : null;
        }

        /**
         * Method carves the contents from the file and
         * creates structure view elements for display
         * @return
         */
        @NotNull
        @Override
        public TreeElement[] getChildren() {
            // Do it only for the CMake file root element
            if (myElement instanceof CMakeFile) {
                final List<TreeElement> treeElements = new ArrayList<TreeElement>();
                // Show function definitions
                List<PsiElement> funmacro = CMakeParserUtilImpl.getDefinedSymbols(myElement);
                for (PsiElement e : funmacro) {
                    treeElements.add(new CMakeViewElement(e));
                }
                // Show variables
                List<PsiElement> vars = CMakeParserUtilImpl.getDefinedVars(myElement);
                for (PsiElement e : vars) {
                    treeElements.add(new CMakeViewElement(e));
                }
                // TODO: Show libraries
                // TODO: Show executables
                return treeElements.toArray(new TreeElement[treeElements.size()]);
            } else {
                return EMPTY_ARRAY;
            }
        }
    }
}
