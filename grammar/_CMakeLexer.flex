package cmake.parsing;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static cmake.psi.CMakeTypes.*;
import java.util.LinkedList;

%%

%{
  public _CMakeLexer() {
    this((java.io.Reader)null);
  }

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

%public
%class _CMakeLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

ENDFUNCTION=endfunction|ENDFUNCTION
FUNCTION=function|FUNCTION
ELSEIF=elseif|ELSIF
ELSE=else|ELSE
ENDIF=endif|ENDIF
IF=if|IF
ENDMACRO=endmacro|ENDMACRO
MACRO=macro|MACRO
ENDFOREACH=endforeach|ENDFOREACH
FOREACH=foreach|FOREACH
ENDWHILE=endwhile|ENDWHILE
WHILE=while|ENDWHILE
BRACKET_COMMENT=(\#\[=*\[)([^\]]|\n)*?(\]=*\])
BRACKET_ARGUMENT=(\[=*\[)([^\]]|\n)*?(\]=*\])
LINE_COMMENT=\#.*
QUOTED_ARGUMENT=(\")([^\"]\\\n|[^\"])*(\")
UNQUOTED_ARGUMENT=([^\(\)\#\"\\ ]|\\\( | \\\) | \\\# | \\\" | (\\ ) | \\\\ | \\\$ | \\\@ | \\\^ | \\t | \\r | \\n| \\;)*
IDENTIFIER=[A-Za-z_][A-Za-z0-9_]*

%state IN_ARGLIST
%%
<YYINITIAL> {
  {WHITE_SPACE}            { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "("                      { yypushstate(IN_ARGLIST);return LPAR; }


  {ENDFUNCTION}            { return ENDFUNCTION; }
  {FUNCTION}               { return FUNCTION; }
  {ELSEIF}                 { return ELSEIF; }
  {ELSE}                   { return ELSE; }
  {ENDIF}                  { return ENDIF; }
  {IF}                     { return IF; }
  {ENDMACRO}               { return ENDMACRO; }
  {MACRO}                  { return MACRO; }
  {ENDFOREACH}             { return ENDFOREACH; }
  {FOREACH}                { return FOREACH; }
  {ENDWHILE}               { return ENDWHILE; }
  {WHILE}                  { return WHILE; }
  {BRACKET_COMMENT}        { return BRACKET_COMMENT; }
  {BRACKET_ARGUMENT}       { return BRACKET_ARGUMENT; }
  {LINE_COMMENT}           { return LINE_COMMENT; }
  {QUOTED_ARGUMENT}        { return QUOTED_ARGUMENT; }
  {IDENTIFIER}             { return IDENTIFIER; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
<IN_ARGLIST> {
  {WHITE_SPACE}            { return com.intellij.psi.TokenType.WHITE_SPACE; }
  "("                      { yypushstate(IN_ARGLIST);return LPAR; }
  ")"                      { yypopstate();return RPAR; }
  {BRACKET_COMMENT}        { return BRACKET_COMMENT; }
  {BRACKET_ARGUMENT}       { return BRACKET_ARGUMENT; }
  {LINE_COMMENT}           { return LINE_COMMENT; }
  {QUOTED_ARGUMENT}        { return QUOTED_ARGUMENT; }
  {UNQUOTED_ARGUMENT}      { return UNQUOTED_ARGUMENT; }
  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}