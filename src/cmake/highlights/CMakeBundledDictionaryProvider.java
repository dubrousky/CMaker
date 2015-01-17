package cmake.highlights;

/**
 * Avoid keywords highlights
 */
import com.intellij.spellchecker.BundledDictionaryProvider;

public class CMakeBundledDictionaryProvider implements BundledDictionaryProvider {
    @Override
    public String[] getBundledDictionaries() {
        return new String[]{"/dictionary/cmake.dic"};
    }
}