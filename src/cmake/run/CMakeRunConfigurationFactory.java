package cmake.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

/**
 * Created by alex on 1/24/15.
 */
public class CMakeRunConfigurationFactory extends ConfigurationFactory {
    protected CMakeRunConfigurationFactory() {
        super(CMakeRunConfigurationType.INSTANCE);
    }

    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new CMakeRunConfiguration(project,this);
    }
}
