package cmake.run;

import cmake.icons.CMakeIcons;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by alex on 1/24/15.
 */
public class CMakeRunConfigurationType implements ConfigurationType {
    public static CMakeRunConfigurationType INSTANCE = new CMakeRunConfigurationType();
    
    protected CMakeRunConfigurationType() {}
    
    @Override
    public String getDisplayName() {
        return "CMake";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Run CMake Tool";
    }

    @Override
    public Icon getIcon() {
        return CMakeIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "CMAKE.RUN.CONFIG";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new CMakeRunConfigurationFactory[]{new CMakeRunConfigurationFactory()};
    }
}
