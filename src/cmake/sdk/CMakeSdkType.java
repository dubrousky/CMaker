package cmake.sdk;

import cmake.global.CMakeIcons;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.util.SystemInfo;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * Created by alex on 1/12/15.
 */
public class CMakeSdkType extends SdkType {
    public static final String CMAKE_SDK_TYPE_ID = "CMakeSdk";

    @NotNull
    public static CMakeSdkType getInstance() {
        CMakeSdkType instance = SdkType.findInstance(CMakeSdkType.class);
        assert instance != null : "Make sure CMakeSdkType is registered in plugin.xml";
        return instance;
    }
    
    public CMakeSdkType() {
        super(CMAKE_SDK_TYPE_ID);
    }
    @Nullable
    @Override
    public String suggestHomePath() {
        if (SystemInfo.isWindows) {
            return "C:\\cygwin\\bin";
        }
        else if (SystemInfo.isMac) {
            String macPorts = "/opt/local/bin/cmake";
            if (new File(macPorts).exists()) return macPorts;

            return null;
        }
        else if (SystemInfo.isLinux) {
            return "/usr/bin/cmake";
        }

        return null;
    }

    @Override
    public boolean isValidSdkHome(String s) {
        return false;
    }

    @Override
    public String suggestSdkName(String s, String s1) {
        return null;
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(SdkModel sdkModel, SdkModificator sdkModificator) {
        return null;
    }

    @Override
    public String getPresentableName() {
        return "CMake Installation";
    }

    @Override
    public void saveAdditionalData(SdkAdditionalData sdkAdditionalData, Element element) {

    }

    @NotNull
    @Override
    public Icon getIcon() {
        return CMakeIcons.FILE;
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return getIcon();
    }
}
