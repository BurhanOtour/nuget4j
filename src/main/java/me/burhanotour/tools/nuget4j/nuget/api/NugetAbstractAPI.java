package me.burhanotour.tools.nuget4j.nuget.api;

import me.burhanotour.tools.nuget4j.nuget.cli.NugetProcessBuilderWrapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public abstract class NugetAbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(NugetAbstractAPI.class);

    protected String mCommand;
    private final File mErrorLog;

    public NugetAbstractAPI(String pCommand) {
        super();
        mCommand = pCommand;
        mErrorLog = new File("log/" + "error_" + pCommand + ".log");
    }


    NugetProcessBuilderWrapper createProcess(String... command) {
        LOGGER.debug("create new process for NuGet command: " + mCommand);

        List<String> commands = new LinkedList<String>();
        commands.add("nuget.exe");
        commands.add(mCommand);
        for (String arg : command) {
            commands.add(arg);
        }
        NugetProcessBuilderWrapper retVal = new NugetProcessBuilderWrapper(mErrorLog,
                commands.toArray(new String[commands.size()]));

        return retVal;
    }


}
