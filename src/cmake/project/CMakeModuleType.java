package cmake.project;


import cmake.icons.CMakeIcons;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by alex on 1/16/15.
 */
public class CMakeModuleType extends ModuleType {
    public static final CMakeModuleType
             LIBRARY = new CMakeModuleType();
    public static final CMakeModuleType
            APPLICATION = new CMakeModuleType();
    
    protected CMakeModuleType( ) {
        super("CMAKE");
    }

    @NotNull
    @Override
    public ModuleBuilder createModuleBuilder() {
        return new CMakeModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "CMAKE";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "TODO: add description";
    }

    @Override
    public Icon getBigIcon() {
        return CMakeIcons.FILE;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return null;
    }
}
