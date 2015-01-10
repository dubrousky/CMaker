package cmake.parsing;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeLexer extends FlexAdapter {
    public CMakeLexer() {
        super(new _CMakeLexer((Reader) null));
    }
}
