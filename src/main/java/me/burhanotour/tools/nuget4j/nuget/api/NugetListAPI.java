package me.burhanotour.tools.nuget4j.nuget.api;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.cli.NugetProcessBuilderWrapper;
import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackage;
import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackageIterator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class NugetListAPI extends NugetAbstractAPI {
    private final static Logger LOGGER = Logger.getLogger(NugetListAPI.class);

    private final String configFileDir;

    public NugetListAPI(String configFileDir) {
        super("list");
        this.configFileDir = configFileDir;
    }

    public Iterator<NugetPackage> list() throws NugetAPIException {
        NugetProcessBuilderWrapper processWrapper = createProcess("-ConfigFile", configFileDir);
        Iterator<NugetPackage> nugetPackageIterator = null;
        try {
            LOGGER.info("using nuget.exe list with configuration file: " + configFileDir);
            try {
                processWrapper.startAndWriteToFile();
            } catch (InterruptedException e) {
                throw new NugetAPIException(e.getMessage(), NugetAPIConstants.ERR_NUGET_LIST_API);
            }
            nugetPackageIterator = new NugetPackageIterator(processWrapper.getOutputFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        processWrapper.deleteOutputFile();
        return nugetPackageIterator;
    }
}

