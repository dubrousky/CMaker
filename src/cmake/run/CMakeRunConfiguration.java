package cmake.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by alex on 1/24/15.
 */
public class CMakeRunConfiguration extends RunConfigurationBase {
    protected CMakeRunConfiguration(Project project, ConfigurationFactory factory) {
        super(project, factory, "CMake Run Configuration");
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new SettingsEditor<CMakeRunConfiguration>() {
            @Override
            protected void resetEditorFrom(CMakeRunConfiguration runConfiguration) {

            }

            @Override
            protected void applyEditorTo(CMakeRunConfiguration runConfiguration) throws ConfigurationException {

            }

            @NotNull
            @Override
            protected JComponent createEditor() {
                return new JLabel("Not implemented");
            }
        };
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return null;
    }
}
