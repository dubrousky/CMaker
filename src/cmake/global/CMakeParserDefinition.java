package cmake.global;

import cmake.psi.CMakeBbegin;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.intellij.plugins.relaxNG.compact.RncTokenTypes;
import org.jetbrains.annotations.NotNull;
import cmake.psi.CMakeTypes;
import cmake.parsing.CMakeLexer;
import cmake.parsing.CMakeParser;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(Language.<CMakeLanguage>findInstance(CMakeLanguage.class));
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new CMakeLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new CMakeParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(com.intellij.psi.TokenType.WHITE_SPACE);
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.create(CMakeTypes.LINE_COMMENT,CMakeTypes.BRACKET_COMMENT);
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() { return TokenSet.create(CMakeTypes.QUOTED_ARGUMENT); }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return CMakeTypes.Factory.createElement(astNode);
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new CMakeFile(fileViewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        // Tune the separator behavior between the tockens
        if( astNode.getElementType() == CMakeTypes.FILE_ELEMENT 
                && !getCommentTokens().contains(astNode1.getElementType())  )
            return SpaceRequirements.MUST_LINE_BREAK;
        if( (astNode.getElementType() == CMakeTypes.ARGUMENT && astNode1.getElementType() == CMakeTypes.ARGUMENT))
            return SpaceRequirements.MUST;
        if( (astNode.getElementType() == CMakeTypes.COMMAND_NAME))
            return SpaceRequirements.MUST_NOT;
        return SpaceRequirements.MAY;
    }
}
