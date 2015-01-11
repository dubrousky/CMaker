package cmake.global;


import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;


/**
 * Created by alex on 12/27/14.
 */
public class CMakeColorsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] ATTRS;

    static {
        ATTRS = new AttributesDescriptor[CMakeHighlighter.DISPLAY_NAMES.size()];
        TextAttributesKey[] keys = CMakeHighlighter.DISPLAY_NAMES.keySet().toArray(new TextAttributesKey[0]);
        for (int i = 0; i < keys.length; i++) {
            TextAttributesKey key = keys[i];
            String name = CMakeHighlighter.DISPLAY_NAMES.get(key).getFirst();
            ATTRS[i] = new AttributesDescriptor(name, key);
        }
    }

    @NotNull
    public String getDisplayName() {
        return "CMake";//CMakeBundle.message("");
    }

    public Icon getIcon() {
        return CMakeIcons.FILE;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new CMakeHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return "# Single line comment\n" +
                "project(HELLO)\n" +
                "#[[\n" +
                "   Multiline comment \n" +
                "]]\n" +
                "if(WIN32)\n" +
                "   add_executable(hello hello.cxx)\n" +
                "endif(WIN32)\n" +
                "function(test)\n" +
                "   message(\"Error\")\n" +
                "   return(${var})\n" +
                "endfunction()\n"
                ;
    }

    private static Map<String, TextAttributesKey> ATTRIBUTES_KEY_MAP = ContainerUtil.newHashMap();

    static {
        
        ATTRIBUTES_KEY_MAP.put("k", CMakeHighlighter.KEYWORD);
        ATTRIBUTES_KEY_MAP.put("s", CMakeHighlighter.STRING);
        ATTRIBUTES_KEY_MAP.put("c", CMakeHighlighter.COMMENT);
        ATTRIBUTES_KEY_MAP.put("bc", CMakeHighlighter.BLOCK_COMMENT);
        ATTRIBUTES_KEY_MAP.put("i", CMakeHighlighter.IDENTIFIER);
        
        
    }
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ATTRIBUTES_KEY_MAP;
    }
}