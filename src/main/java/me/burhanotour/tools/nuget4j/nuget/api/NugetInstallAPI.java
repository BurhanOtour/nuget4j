package me.burhanotour.tools.nuget4j.nuget.api;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.cli.NugetProcessBuilderWrapper;
import com.wincornixdorf.platform.probase.util.process.ProcessException;
import org.apache.log4j.Logger;

public class NugetInstallAPI extends NugetAbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(NugetInstallAPI.class);

    private final String outputDir;

    private final String configFileDir;

    public NugetInstallAPI(String outputDir, String configFileDir) {
        super("install");
        this.outputDir = outputDir;
        this.configFileDir = configFileDir;
    }

    public int install(String packageName) throws NugetAPIException {
        LOGGER.info("Installing " + packageName);
        NugetProcessBuilderWrapper processWrapper = createProcess(packageName, "-OutputDirectory", outputDir, "-ConfigFile", configFileDir);
        int exitValue;
        try {
            exitValue = processWrapper.start();
        } catch (ProcessException e) {
            throw new NugetAPIException("Failed to install " + packageName, NugetAPIConstants.ERR_NUGET_INSTALL_API);
        }
        LOGGER.info("Installing " + packageName + " finished");
        return exitValue;
    }
}
