package cmake.global;

import cmake.psi.CMakeArgument;
import cmake.psi.CMakeCommandName;
import cmake.psi.CMakeIfExpr;
import cmake.psi.CMakeTypes;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtil;
import com.intellij.psi.util.PsiUtilBase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Annotator does a psi aware highlighting when highlighter
 * does a token-based position independent coloring.
 */
public class CMakeAnnotator implements Annotator {
    static final Set<String> keywords = new HashSet<String>(); 
    {
        final String[] a = {
                "option",
                "add_custom_command",
                "add_custom_target",
                "add_definitions",
                "add_dependencies",
                "add_executable",
                "add_library",
                "add_subdirectory",
                "add_test",
                "aux_source_directory",
                "break",
                "build_command",
                "cmake_minimum_required",
                "cmake_policy",
                "configure_file",
                "create_test_sourcelist",
                "define_property",
                "else",
                "elseif",
                "enable_language",
                "enable_testing",
                "endif",
                "endforeach",
                "endfunction",
                "endmacro",
                "endwhile",
                "execute_process",
                "export",
                "file",
                "find_file",
                "find_library",
                "find_package",
                "find_path",
                "find_program",
                "fltk_wrap_ui",
                "foreach",
                "function",
                "get_cmake_property",
                "get_directory_property",
                "get_filename_component",
                "get_property",
                "get_source_file_property",
                "get_target_property",
                "get_test_property",
                "if",
                "include",
                "include_directories",
                "include_external_msproject",
                "include_regular_expression",
                "install",
                "link_directories",
                "list",
                "load_cache",
                "load_command",
                "macro",
                "mark_as_advanced",
                "math",
                "message",
                "option",
                "project",
                "qt_wrap_cpp",
                "qt_wrap_ui",
                "remove_definitions",
                "return",
                "separate_arguments",
                "set",
                "set_directory_properties",
                "set_property",
                "set_source_files_properties",
                "set_target_properties",
                "set_tests_properties",
                "site_name",
                "source_group",
                "string",
                "target_link_libraries",
                "try_compile",
                "try_run",
                "unset",
                "variable_watch",
                "while"
        };
        Collections.addAll(keywords, a);

    }
    @Override
    public void annotate(PsiElement psiElement, AnnotationHolder annotationHolder) {
//        // TODO: check if psiElement of required type
        if (psiElement instanceof CMakeCommandName) {
//          // TODO: get project, extract file elements
//          Project project = element.getProject();
//          List<SimpleProperty> properties = SimpleUtil.findProperties(project, value.substring(7));
//          // TODO: create annotation at text range
            if(keywords.contains(psiElement.getText())) {
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset(),
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength());
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.KEYWORD);
//              TODO: use for error annotation
//                    TextRange range = new TextRange(psiElement.getTextRange().getStartOffset() + 8,
//                            psiElement.getTextRange().getEndOffset());
//                    holder.createErrorAnnotation(range, "Unresolved property");
//
            }
        }
        else if(psiElement.getNode().getElementType()== CMakeTypes.IF
                ||psiElement.getNode().getElementType()== CMakeTypes.ELSE
                ||psiElement.getNode().getElementType()== CMakeTypes.ELSEIF
                ||psiElement.getNode().getElementType()== CMakeTypes.ENDIF
                ||psiElement.getNode().getElementType()== CMakeTypes.FUNCTION
                ||psiElement.getNode().getElementType()== CMakeTypes.MACRO
                ||psiElement.getNode().getElementType()== CMakeTypes.ENDFUNCTION
                ||psiElement.getNode().getElementType()== CMakeTypes.ENDMACRO
                ||psiElement.getNode().getElementType()== CMakeTypes.WHILE
                ||psiElement.getNode().getElementType()== CMakeTypes.FOREACH
                ||psiElement.getNode().getElementType()== CMakeTypes.ENDWHILE
                ||psiElement.getNode().getElementType()== CMakeTypes.ENDFOREACH
                )
        {
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset(),
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength());
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.KEYWORD);
        }
    }
}
