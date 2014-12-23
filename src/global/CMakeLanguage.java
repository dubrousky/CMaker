package global;

import com.intellij.lang.Language;

/**
 * Created by alex on 12/21/14.
 */
public class CMakeLanguage extends Language {
    public static final CMakeLanguage INSTANCE = new CMakeLanguage();

    private CMakeLanguage() {
        super("CMake");
    }
}
