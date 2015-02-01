package cmake.run;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alex on 1/24/15.
 */
public class CMakeRunner extends DefaultProgramRunner {
    String executorId ="CMakeRunner";
    @NotNull
    @Override
    public String getRunnerId() {
        return null;
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) &&
                (runProfile instanceof CMakeRunConfiguration );
    }
}
