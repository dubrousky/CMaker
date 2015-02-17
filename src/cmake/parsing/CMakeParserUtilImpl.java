package cmake.parsing;

import cmake.filetypes.CMakeFile;
import cmake.filetypes.CMakeFileType;
import cmake.icons.CMakeIcons;
import cmake.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.PairProcessor;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by alex on 1/6/15.
 */
public class CMakeParserUtilImpl {
    public static String getName(CMakeCommandName element) {
        return element.getText();
    }

    public static PsiElement setName(CMakeCommandName element, String newName) {
        ASTNode commandNameNode = element.getFirstChild().getNode();
// FIXME: node replacement
//        if (commandNameNode != null) {
//            CMakeCommandName newNode = CMakeTypes.Factory.createElement(new CMakeN)
//            ASTNode newKeyNode = property.getFirstChild().getNode();
//            element.getNode().replaceChild(commandNameNode, newKeyNode);
//        }
        return element;
    }

    public static class CMakeNamedElementFactory {
//        public static CMake createElement(Project project, String name) {
//            final CMakeFile file = createFile(project, name);
        // TODO: use tree walker to get command name in AST
//            return (SimpleProperty) file.getFirstChild();
//        }

        public static CMakeFile createFile(Project project, String text) {
            String name = "cmake.dummy";
            return (CMakeFile) PsiFileFactory.getInstance(project).
                    createFileFromText(name, CMakeFileType.INSTANCE, text);
        }
    }

    public static PsiElement getNameIdentifier(CMakeCommandName element) {
        ASTNode keyNode = element.getNode();
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }
    @NotNull
    public static ItemPresentation getPresentation(final CMakeCommandExpr element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                StringBuilder stringBuilder = new StringBuilder();
                CMakeArguments args = ((CMakeCommandExpr) element).getArguments();
                if(element.getFirstChild().getText().contains("set")
                        || element.getFirstChild().getText().contains("list")
                        || element.getFirstChild().getText().contains("option"))
                {
                    stringBuilder.append(args.getArgument());
                    stringBuilder.append(" : ");
                    stringBuilder.append(element.getFirstChild().getText());
                }    
                else
                {
                    if (args.getTextLength() > 0) {
                        stringBuilder.append(args.getArgument().getText())
                                .append("(");
                        for (PsiElement a : args.getSeparatedArgumentList()) {
                            stringBuilder.append(" ");
                            stringBuilder.append(a.getText());
                        }
                        stringBuilder.append(" ) : ")
                                .append(element.getFirstChild().getText());
                    }
                }
                return  stringBuilder.toString();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                
                if(element.getFirstChild().getText().contains("function"))
                    return CMakeIcons.FUN;
                else if(element.getFirstChild().getText().contains("macro"))
                    return CMakeIcons.MACRO;
                else if(element.getFirstChild().getText().contains("set")
                            || element.getFirstChild().getText().contains("list")
                            || element.getFirstChild().getText().contains("option"))
                {
                    return CMakeIcons.FILE;
                }
                return null;
            }
        };
    }

    public static List<PsiElement> getDefinedSymbols(Project project) {
        final List<PsiElement> result = new ArrayList<PsiElement>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, 
                CMakeFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        // Scan each file for named entities
        for (VirtualFile virtualFile : virtualFiles) {
            CMakeFile cmakeFile = (CMakeFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cmakeFile != null) {
                cmakeFile.accept(new PsiRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitElement(PsiElement element) {
                        super.visitElement(element);
                        if(element instanceof CMakeCommandExpr
                                && (element.getFirstChild().getNode().getElementType() == CMakeTypes.FUNCTION
                                || element.getFirstChild().getNode().getElementType() == CMakeTypes.MACRO
                                || element.getFirstChild().toString().equalsIgnoreCase("set")))
                        {
                            result.add(element);
                        }
                    }
                });
            }
        }
        return result;
    }

    public static List<PsiElement> getDefinedSymbols(Project project, final String name) {
        final List<PsiElement> result = new ArrayList<PsiElement>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                CMakeFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        // Scan each file for named entities
        for (VirtualFile virtualFile : virtualFiles) {
            CMakeFile cmakeFile = (CMakeFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cmakeFile != null) {
                cmakeFile.accept(new PsiRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitElement(PsiElement element) {
                        super.visitElement(element);
                        if(element instanceof CMakeCommandExpr
                                && (element.getFirstChild().getNode().getElementType() == CMakeTypes.FUNCTION
                                    || element.getFirstChild().getNode().getElementType() == CMakeTypes.MACRO))
                        {
                            result.add(element);
                        }
                    }
                });   
            }
        }
        return result;
    }

    public static List<PsiElement> getDefinedSymbols(PsiElement root) {
        final List<PsiElement> result = new ArrayList<PsiElement>();
        if(root instanceof CMakeFile){
            root.accept(new PsiRecursiveElementWalkingVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);
                    if(element instanceof CMakeCommandExpr
                            && (element.getFirstChild().getNode().getElementType() == CMakeTypes.FUNCTION
                            || element.getFirstChild().getNode().getElementType() == CMakeTypes.MACRO))
                    {
                        result.add(element);
                    }
                }
            });
        }
        return result;
    }
    
    
    public static List<PsiElement> getDefinedVars(Project project, String name) {
        final List<PsiElement> result = new ArrayList<PsiElement>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                CMakeFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        // Scan each file for named entities
        for (VirtualFile virtualFile : virtualFiles) {
            CMakeFile cmakeFile = (CMakeFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cmakeFile != null) {
                cmakeFile.accept(new PsiRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitElement(PsiElement element) {
                        super.visitElement(element);
                        if(element instanceof CMakeCommandExpr
                                && (element.getFirstChild().getText().contains("set")
                                || element.getFirstChild().getText().contains("list")
                                || element.getFirstChild().getText().contains("option")))
                        {
                            result.add(element);
                        }
                    }
                });
            }
        }
        return result;
    }

    public static List<PsiElement> getDefinedVars(PsiElement root) {
        final List<PsiElement> result = new ArrayList<PsiElement>();
        if(root instanceof CMakeFile){
            root.accept(new PsiRecursiveElementWalkingVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);
                    if(element instanceof CMakeCommandExpr
                            && (element.getFirstChild().getText().contains("set")
                            || element.getFirstChild().getText().contains("list")
                            || element.getFirstChild().getText().contains("option")))
                    {
                        result.add(element);
                    }
                }
            });
        }
        return result;
    }
}
