package cmake.highlights;

import cmake.psi.*;
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
        // Annotate and highlight the cmake keywords 
        if (psiElement instanceof CMakeCommandName) {
            if(keywords.contains(psiElement.getText().toLowerCase())) {
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset(),
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength());
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.KEYWORD);
            }
        }
        // Also highlight block keywords
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
        // Annotate logical operators
        else if(psiElement instanceof CMakeUnquotedArgument
                && (psiElement.getText().contains("NOT")
                        || psiElement.getText().contains("AND")
                        || psiElement.getText().contains("OR")
                        || psiElement.getText().contains("STREQUAL"))
                ){
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset(),
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength());
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.OPERATION_SIGN);
        }
        // Annotate logical constants
        else if(psiElement instanceof CMakeUnquotedArgument
                && (psiElement.getText().contains("ON")
                || psiElement.getText().contains("OFF")
                || psiElement.getText().contains("TRUE")
                ||psiElement.getText().contains("FALSE") )
                ){
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset(),
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength());
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.CONSTANT);
        }
        // Annotate variable expansion
        else if(psiElement.getNode().getElementType() == CMakeTypes.VAR_REF) {
                TextRange range = new TextRange(psiElement.getTextRange().getStartOffset()+2,
                        psiElement.getTextRange().getStartOffset() + psiElement.getTextRange().getLength()-1);
                Annotation annotation = annotationHolder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL); 
                
        }
        // Annotate missing function name
        else if(psiElement instanceof CMakeFbegin){
                CMakeArguments args = ((CMakeFbegin) psiElement).getCommandExpr().getArguments();
                if( null == args.getArgument()) {
                        TextRange range = new TextRange(args.getTextRange().getStartOffset(),
                                args.getTextRange().getStartOffset() + args.getTextRange().getLength());
                        Annotation annotation = annotationHolder.createErrorAnnotation(range, "Function must have a name");
                }
        }
        // Annotate missing macro name
        else if(psiElement instanceof CMakeMbegin){
                CMakeArguments args = ((CMakeMbegin) psiElement).getCommandExpr().getArguments();
                if( null == args.getArgument()) {
                        TextRange range = new TextRange(args.getTextRange().getStartOffset(),
                                args.getTextRange().getStartOffset() + args.getTextRange().getLength());
                        Annotation annotation = annotationHolder.createErrorAnnotation(range, "Macro must have a name");
                }
        }
        // Annotate missing predicate condition
        else if(psiElement instanceof CMakeIfExpr){
                CMakeArguments args = ((CMakeIfExpr) psiElement).getCommandExpr().getArguments();
                if( null == args.getArgument()) {
                        TextRange range = new TextRange(args.getTextRange().getStartOffset(),
                                args.getTextRange().getStartOffset() + args.getTextRange().getLength());
                        Annotation annotation = annotationHolder.createErrorAnnotation(range, "Predicate such as if/elsif must have a condition");
                }
        }
        // Annotate missing predicate condition
        else if(psiElement instanceof CMakeElseifExpr) {
                CMakeArguments args = ((CMakeElseifExpr) psiElement).getCommandExpr().getArguments();
                if (null == args.getArgument()) {
                        TextRange range = new TextRange(args.getTextRange().getStartOffset(),
                                args.getTextRange().getStartOffset() + args.getTextRange().getLength());
                        Annotation annotation = annotationHolder.createErrorAnnotation(range, "Predicate such as if/elsif must have a condition");
                }
        }
        // Annotate string ops
        /*
        string(REGEX MATCH <regular_expression>
       <output variable> <input> [<input>...])
        string(REGEX MATCHALL <regular_expression>
       <output variable> <input> [<input>...])
        string(REGEX REPLACE <regular_expression>
       <replace_expression> <output variable>
       <input> [<input>...])
        string(REPLACE <match_string>
       <replace_string> <output variable>
       <input> [<input>...])
        string(CONCAT <output variable> [<input>...])
        string(<MD5|SHA1|SHA224|SHA256|SHA384|SHA512>
       <output variable> <input>)
        string(COMPARE EQUAL <string1> <string2> <output variable>)
        string(COMPARE NOTEQUAL <string1> <string2> <output variable>)
        string(COMPARE LESS <string1> <string2> <output variable>)
        string(COMPARE GREATER <string1> <string2> <output variable>)
        string(ASCII <number> [<number> ...] <output variable>)
        string(CONFIGURE <string1> <output variable>
       [@ONLY] [ESCAPE_QUOTES])
        string(TOUPPER <string1> <output variable>)
        string(TOLOWER <string1> <output variable>)
        string(LENGTH <string> <output variable>)
        string(SUBSTRING <string> <begin> <length> <output variable>)
        string(STRIP <string> <output variable>)
        string(RANDOM [LENGTH <length>] [ALPHABET <alphabet>]
       [RANDOM_SEED <seed>] <output variable>)
        string(FIND <string> <substring> <output variable> [REVERSE])
        string(TIMESTAMP <output variable> [<format string>] [UTC])
        string(MAKE_C_IDENTIFIER <input string> <output variable>)
        */
    }
}
