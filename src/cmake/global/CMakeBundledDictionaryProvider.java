package cmake.global;

/**
 * Created by alex on 12/27/14.
 */
import com.intellij.spellchecker.BundledDictionaryProvider;

public class CMakeBundledDictionaryProvider implements BundledDictionaryProvider {
    @Override
    public String[] getBundledDictionaries() {
        return new String[]{"/dictionary/cmake.dic"};
    }
}