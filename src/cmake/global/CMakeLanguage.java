package cmake.global;

import com.intellij.lang.Language;

/**
 * Definition of thew language type for CMake
 * and singleton.
 */
public class CMakeLanguage extends Language {
    public static final CMakeLanguage INSTANCE = new CMakeLanguage();

    private CMakeLanguage() {
        super("CMake");
    }
}
