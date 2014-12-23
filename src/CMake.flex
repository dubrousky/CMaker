package parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;


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
IDENTIFIER=[A-Za-z_][A-Za-z0-9_]*
SPACE=[ \t]+
NEWLINE=\n | \r\n | \r
FILE= {FILE_ELEMENT}*
FILE_ELEMENT={COMMAND_INVOCATION} {LINE_ENDING} | ({BRACKET_COMMENT} | {SPACE})* {LINE_ENDING}
LINE_ENDING={LINE_COMMENT}? {NEWLINE}

COMMAND_INVOCATION={SPACE}* {IDENTIFIER} {SPACE}* {LEFT_BRACE} {ARGUMENTS} {RIGHT_BRACE}
BLOCK_IDENTIFIER="if"|"function"|"foreach"|"while"
BLOCK_IDENTIFIER_END="endif"|"endfunction"|"endforeach"|"endwhile"
ARGUMENTS={ARGUMENT}? {SEPARATED_ARGUMENTS}*
SEPARATED_ARGUMENTS={SEPARATION}+ {ARGUMENT}? | {SEPARATION}* "(" {ARGUMENTS} ")"
SEPARATION={SPACE} | {LINE_ENDING}

ARGUMENT=  {QUOTED_ARGUMENT} | {UNQUOTED_ARGUMENT}

QUOTED_ARGUMENT=  '\"' {QUOTED_ELEMENT}* '\"'
QUOTED_ELEMENT=  ![\"] | {ESCAPE_SEQUENCE} | {QUOTED_CONTINUATION}
QUOTED_CONTINUATION='\' {NEWLINE}

UNQUOTED_ARGUMENT={UNQUOTED_ELEMENT}+
UNQUOTED_ELEMENT= ![\(\)#\"\\] | {ESCAPE_SEQUENCE}

ESCAPE_SEQUENCE=  {ESCAPE_IDENTITY} | {ESCAPE_ENCODED} | {ESCAPE_SEMICOLON}
ESCAPE_IDENTITY="\(" | "\)" | "\#" | "\"" | "\ " | "\\" | "\$" | "\@" | "\^"
ESCAPE_ENCODED="\t" | "\r" | "\n"
ESCAPE_SEMICOLON="\;"

LINE_COMMENT='#'


%state IN_FUNCTION
%state IN_FOR
%state IN_WHILE
%state IN_MACRO
%state IN_IF
%state IN_COMMAND
%state IN_COMMENT
%state IN_STRING
%state IN_ARGLIST
%state IN_BLOCK_COMMAND
%state IN_BODY

%%
<YYINITIAL> {
    "\""  {yybegin(IN_STRING); return CMakeElementTypes.STRING;}
    "#"   {yybegin(IN_COMMENT);return CMakeElementTypes.COMMENT;}
    {NEWLINE} {return CMakeElementTypes.WHITE_SPACE;}
    {SPACE}* {BLOCK_IDENTIFIER} {SPACE}* {yybegin(IN_BLOCK_COMMAND);return CMakeElementTypes.KEYWORD;}
    {SPACE}* {IDENTIFIER} {SPACE}* {yybegin(IN_COMMAND);return CMakeElementTypes.KEYWORD;}
    {SPACE}* {return CMakeElementTypes.WHITE_SPACE;}
    .  {return CMakeElementTypes.BAD_CHARACTER;}
}

<IN_BLOCK_COMMAND,IN_ARGLIST> {
    {LEFT_BRACE} {yybegin(IN_ARGLIST); return CMakeElementTypes.LEFT_BRACE;}
    "#"   {yybegin(IN_COMMENT);return CMakeElementTypes.COMMENT;}
    {SPACE}* {BLOCK_IDENTIFIER_END} {SPACE}* {yybegin(YYINITIAL); return CMakeElementTypes.WHITE_SPACE;}
    <IN_ARGLIST> {
        "\""  {yybegin(IN_STRING); return CMakeElementTypes.STRING;}
        {RIGHT_BRACE} {yybegin(IN_BLOCK_COMMAND); return CMakeElementTypes.RIGHT_BRACE;}
        {IDENTIFIER} {return CMakeElementTypes.VAR;}
        {SEPARATION} {return CMakeElementTypes.WHITE_SPACE;}
        . {return CMakeElementTypes.BAD_CHARACTER;}
    }

}



<IN_COMMAND,IN_ARGLIST> {
    {LEFT_BRACE} {yybegin(IN_ARGLIST); return CMakeElementTypes.LEFT_BRACE;}
    <IN_ARGLIST> {
        "\""  {yybegin(IN_STRING); return CMakeElementTypes.STRING;}
        {RIGHT_BRACE} {yybegin(YYINITIAL); return CMakeElementTypes.RIGHT_BRACE;}
        {IDENTIFIER} {return CMakeElementTypes.VAR;}
        . {return CMakeElementTypes.WHITE_SPACE;}

    }
}



<IN_COMMENT> {
    {NEWLINE} {yybegin(YYINITIAL);return CMakeElementTypes.COMMENT; }
    . {return CMakeElementTypes.COMMENT; }
}

<IN_STRING> {
    "\"" {yybegin(YYINITIAL); return CMakeElementTypes.STRING;}
    {ESCAPE_SEQUENCE} {return CMakeElementTypes.STRING;}
     .  {return CMakeElementTypes.STRING;}
}
