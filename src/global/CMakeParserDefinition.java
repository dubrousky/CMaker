package global;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import parsing.CMakeElementTypes;
import parsing.CMakeLexer;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeParserDefinition implements ParserDefinition {
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
        return null;//CMakeElementTypes.FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return CMakeElementTypes.WHITESPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return CMakeElementTypes.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() { return CMakeElementTypes.STRINGS; }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return null;
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return null;//new CMakeFileImpl(fileViewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        return SpaceRequirements.MAY;
    }
}
