package parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

%%

%class _CMakeLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%debug
%eof{ return;
%eof}

%{
    // Stolen from Mathematica support plugin
    // This adds support for nested states. I'm no JFlex pro, so maybe this is overkill, but it works quite well.
    private final LinkedList<Integer> states = new LinkedList();

    // Scope names that needs to be tracked when entering block
    private final LinkedList<Integer> names = new LinkedList();

    // Known ids (used before or set)
    private final Set<String> known_ids = new HashSet<String>();

    private void yypushstate(int state) {
        states.addFirst(yystate());
        yybegin(state);
    }
    private void yypopstate() {
        final int state = states.removeFirst();
        yybegin(state);
    }

%}

LEFT_BRACE="("
RIGHT_BRACE=")"
IDENTIFIER= [A-Za-z_][A-Za-z0-9_.]*
NUMBER=[0-9]+ (\.[0-9]*)+
SPACE=[ \t]+
NEWLINE={SPACE}* (\n | \r\n | \r)
FILE= {FILE_ELEMENT}*
FILE_ELEMENT={COMMAND_INVOCATION} {LINE_ENDING} | ({BRACKET_COMMENT} | {SPACE})* {LINE_ENDING}
LINE_ENDING={LINE_COMMENT}? {NEWLINE}
VAR_EXPANSION=\$\{ {IDENTIFIER} \}

COMMAND_INVOCATION={SPACE}* {IDENTIFIER} {SPACE}* {LEFT_BRACE} {ARGUMENTS} {RIGHT_BRACE}
BLOCK_IDENTIFIER="if"|"function"|"foreach"|"while"
BLOCK_IDENTIFIER_END="endif"|"endfunction"|"endforeach"|"endwhile"
ARGUMENTS={ARGUMENT}? {SEPARATED_ARGUMENTS}*
SEPARATED_ARGUMENTS={SEPARATION}+ {ARGUMENT}? | {SEPARATION}* "(" {ARGUMENTS} ")"
SEPARATION={SPACE} | {LINE_ENDING}

ARGUMENT=  {QUOTED_ARGUMENT} | {UNQUOTED_ARGUMENT}

QUOTED_ARGUMENT=  [\"] {QUOTED_ELEMENT}* [\"]
QUOTED_ELEMENT=  [^\"] | {ESCAPE_SEQUENCE} | {QUOTED_CONTINUATION}
QUOTED_CONTINUATION="\\" {NEWLINE}

UNQUOTED_ARGUMENT={UNQUOTED_ELEMENT}+
UNQUOTED_ELEMENT= [^\(\)\#\"\\] | {ESCAPE_SEQUENCE}

ESCAPE_SEQUENCE=  {ESCAPE_IDENTITY} | {ESCAPE_ENCODED} | {ESCAPE_SEMICOLON}
ESCAPE_IDENTITY="\\\(" | "\\\)" | "\\\#" | "\\\"" | "\\\ " | "\\\\" | "\\\$" | "\\\@" | "\\\^"
ESCAPE_ENCODED="\\t" | "\\r" | "\\n"
ESCAPE_SEMICOLON="\\;"

LINE_COMMENT="#" [^\n\r]*
BLOCK_COMMENT_BEGIN="[" "="* "["
BLOCK_COMMENT_END="]" "="* "]"

KEYWORD="add_custom_command"|
        "add_custom_target"|
        "add_definitions"|
        "add_dependencies"|
        "add_executable"|
        "add_library"|
        "add_subdirectory"|
        "add_test"|
        "aux_source_directory"|
        "break"|
        "build_command"|
        "cmake_minimum_required"|
        "cmake_policy"|
        "configure_file"|
        "create_test_sourcelist"|
        "define_property"|
        "enable_language"|
        "enable_testing"|
        "execute_process"|
        "export"|
        "file"|
        "find_file"|
        "find_library"|
        "find_package"|
        "find_path"|
        "find_program"|
        "fltk_wrap_ui"|
        "get_cmake_property"|
        "get_directory_property"|
        "get_filename_component"|
        "get_property"|
        "get_source_file_property"|
        "get_target_property"|
        "get_test_property"|
        "include"|
        "include_directories"|
        "include_external_msproject"|
        "include_regular_expression"|
        "install"|
        "link_directories"|
        "list"|
        "load_cache"|
        "load_command"|
        "mark_as_advanced"|
        "math"|
        "message"|
        "option"|
        "project"|
        "qt_wrap_cpp"|
        "qt_wrap_ui"|
        "remove_definitions"|
        "return"|
        "separate_arguments"|
        "set"|
        "set_directory_properties"|
        "set_property"|
        "set_source_files_properties"|
        "set_target_properties"|
        "set_tests_properties"|
        "site_name"|
        "source_group"|
        "string"|
        "target_link_libraries"|
        "try_compile"|
        "try_run"|
        "unset"|
        "variable_watch"

%state IN_FUNCTION
%state IN_FOR
%state IN_WHILE
%state IN_MACRO
%state IN_IF
%state IN_COMMAND
%state IN_BLOCK_COMMENT
%state IN_ARGLIST
%state IN_BLOCK_COMMAND
%state IN_BLOCK_COMMAND_END
%state IN_BODY

%%
<YYINITIAL> {
    {SPACE}* {BLOCK_COMMENT_BEGIN} {yypushstate(IN_BLOCK_COMMENT);return CMakeElementTypes.BLOCK_COMMENT;}
    {SPACE}* {LINE_COMMENT}   {return CMakeElementTypes.COMMENT;}
    {NEWLINE} {return CMakeElementTypes.WHITE_SPACE;}
    {SPACE}* {BLOCK_IDENTIFIER} {SPACE}* {yypushstate(IN_BLOCK_COMMAND);return CMakeElementTypes.KEYWORD;}
    {SPACE}* ({KEYWORD}|{IDENTIFIER}) {SPACE}* {yypushstate(IN_COMMAND);return CMakeElementTypes.KEYWORD;}
    {SPACE}* {return CMakeElementTypes.WHITE_SPACE;}
    .  {return CMakeElementTypes.BAD_CHARACTER;}
}

<IN_BLOCK_COMMAND> {
    {SPACE}* {BLOCK_COMMENT_BEGIN} {yypushstate(IN_BLOCK_COMMENT);return CMakeElementTypes.BLOCK_COMMENT;}
    {SPACE}* {LINE_COMMENT}   {return CMakeElementTypes.COMMENT;}
    {SPACE}* {NEWLINE} {return CMakeElementTypes.WHITE_SPACE;}
    {SPACE}* {BLOCK_IDENTIFIER_END} {SPACE}* { yypopstate(); yypushstate(IN_BLOCK_COMMAND_END); return CMakeElementTypes.KEYWORD;}
    {SPACE}* {BLOCK_IDENTIFIER} {SPACE}* {yypushstate(IN_BLOCK_COMMAND);return CMakeElementTypes.KEYWORD;}
    {SPACE}* {IDENTIFIER} {SPACE}* {yypushstate(IN_COMMAND);return CMakeElementTypes.KEYWORD;}
    {LEFT_BRACE} {yypushstate(IN_ARGLIST); return CMakeElementTypes.LEFT_BRACE;}
    .  {return CMakeElementTypes.BAD_CHARACTER;}
 }

<IN_COMMAND,IN_BLOCK_COMMAND_END> {
    {SPACE}* {BLOCK_COMMENT_BEGIN} {yypushstate(IN_BLOCK_COMMENT);return CMakeElementTypes.BLOCK_COMMENT;}
    {SPACE}* {LINE_COMMENT}   {return CMakeElementTypes.COMMENT;}
    {LEFT_BRACE} {yypushstate(IN_ARGLIST); return CMakeElementTypes.LEFT_BRACE;}
    {SPACE}* {NEWLINE} {yypopstate(); return CMakeElementTypes.WHITE_SPACE;}
    . {return CMakeElementTypes.BAD_CHARACTER;}
}

<IN_ARGLIST> {
    {SPACE}* {BLOCK_COMMENT_BEGIN} {yypushstate(IN_BLOCK_COMMENT);return CMakeElementTypes.BLOCK_COMMENT;}
    {QUOTED_ARGUMENT} {return CMakeElementTypes.STRING;}
    {RIGHT_BRACE} {yypopstate(); return CMakeElementTypes.RIGHT_BRACE;}
    {IDENTIFIER} {return CMakeElementTypes.IDENTIFIER;}
    {NUMBER} {return CMakeElementTypes.NUMBER;}
    {SEPARATION} {return CMakeElementTypes.WHITE_SPACE;}
    {NEWLINE} {return CMakeElementTypes.WHITE_SPACE;}
    {VAR_EXPANSION} {return CMakeElementTypes.KEYWORD;}
    . {return CMakeElementTypes.BAD_CHARACTER;}
}

<IN_BLOCK_COMMENT> {
    {BLOCK_COMMENT_END} { yypopstate(); return CMakeElementTypes.BLOCK_COMMENT; }
    {NEWLINE} {return CMakeElementTypes.BLOCK_COMMENT;}
    {ESCAPE_SEQUENCE} {return CMakeElementTypes.ESCAPED_CHAR;}
    . {return CMakeElementTypes.BLOCK_COMMENT; }
}
