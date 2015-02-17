package cmake.doc;

import cmake.psi.CMakeCommandName;
import com.intellij.codeInsight.lookup.LookupEx;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by alex on 1/31/15.
 */
public class CMakeDocumentationProvider extends AbstractDocumentationProvider {
    @Override
    public String generateDoc(PsiElement element, PsiElement originalElement) {
        System.out.println(element.getText());
        //if(originalElement instanceof CMakeCommandName) {
            String kwd = element.getText().toLowerCase(); // TODO: find by lower case and check if it is a keyword
            String path = "/help/";
            path+=kwd+".html";

            InputStream docFile = (path != null) ? CMakeDocumentationProvider.class.getResourceAsStream(path) : null;
            if (docFile != null) {
                System.out.println(path);
                return new Scanner(docFile, "UTF-8").useDelimiter("\\A").next();
            }
        //}
        return "Missing Info";//super.generateDoc(element, originalElement);
    }

    @Override
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (element != null) {
            final LookupEx activeLookup = 
                    LookupManager.getActiveLookup(FileEditorManager.getInstance(element.getProject()).getSelectedTextEditor());
            if (activeLookup != null) {
                if (activeLookup.isFocused()) {
                    //CMakePsiElementFactory elementFactory = new CmakePsiElementFactory(psiManager.getProject());
                    try {
                        return null;
                    //    return elementFactory.createSymbol(object.toString());
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public PsiElement getCustomDocumentationElement(Editor editor, PsiFile file, PsiElement contextElement) {
        return super.getCustomDocumentationElement(editor, file, contextElement);
    }
}
