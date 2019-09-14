package me.burhanotour.tools.nuget4j.nuget.cli;

import java.io.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import me.burhanotour.tools.nuget4j.exceptions.NugetCLIException;
import com.wincornixdorf.platform.probase.util.process.ProcessException;
import com.wincornixdorf.platform.probase.util.process.ProcessRunner;
import com.wincornixdorf.platform.probase.util.process.ProcessRunnerFactory;
import com.wincornixdorf.platform.probase.util.process.StringReturningOutputHandler;
import org.apache.log4j.Logger;

public class NugetProcessBuilderWrapper {
    final static Logger LOGGER = Logger.getLogger(NugetProcessBuilderWrapper.class);

    final String mErrorLog;
    final File mErrorLogFile;

    StringReturningOutputHandler outputStreamHandler;
    StringReturningOutputHandler errorStreamHandler;
    String[] commands;
    private ProcessRunner mProcessRunner;
    private BufferedReader mReader;
    private File outputFile = new File("c:\\tmp\\nuget\\packages_" + new Timestamp(System.currentTimeMillis()).getTime() + ".txt");

    public NugetProcessBuilderWrapper(final File pErrorLog, String... allCommands) {
        outputStreamHandler = new StringReturningOutputHandler();
        errorStreamHandler = new StringReturningOutputHandler();
        mProcessRunner = ProcessRunnerFactory.createProcessRunner();
        mProcessRunner.setStdOutHandler(outputStreamHandler);
        mProcessRunner.setStdErrHandler(errorStreamHandler);
        commands = allCommands;
        mErrorLog = pErrorLog.getAbsolutePath();
        mErrorLogFile = pErrorLog;
    }

    private void handleError(final int retVal) throws NugetCLIException {
        if (0 != retVal) {
            throw new NugetCLIException("failed to execute command: \"" + commands[1] + "\""
                    + ". Please Check error log: " + mErrorLog, retVal);
        }
    }

    public boolean deleteOutputFile(){
        return outputFile.delete();
    }

    public InputStream startAndRead() throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (String s : commands) {
            sb.append(s);
            sb.append(" ");
        }
        String command = "cmd /c " + sb.toString();
        Process process = null;
        executeProcess(process, command);

        // BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return process.getInputStream();
    }

    public File startAndWriteToFile() throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (String s : commands) {
            sb.append(s);
            sb.append(" ");
        }
        String command = "cmd /c " + sb.toString() + " >> " + outputFile.getAbsolutePath();
        Process process = null;
        executeProcess(process, command);

        // BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return outputFile;
    }

    private Process executeProcess(Process p, String command) throws IOException, InterruptedException {
        p = Runtime.getRuntime().exec(command);
        p.waitFor(4, TimeUnit.SECONDS);
        return p;
    }

    public int start() throws ProcessException {
        int exitVal = Integer.MIN_VALUE;
        this.probaseExecute();
        return exitVal;
    }

    public File getOutputFile() {
        return outputFile;
    }

    private int probaseExecute() throws ProcessException {
        int retVal = mProcessRunner.execute(commands);
        handleError(errorStreamHandler.getOutput());
        return retVal;
    }

    public void handleError(String error) {
        if (!error.isEmpty()) {
            try {
                FileWriter writer = new FileWriter(mErrorLogFile);
                writer.write(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
