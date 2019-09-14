package nuget.cli.probase;

import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;
import com.wincornixdorf.platform.probase.util.process.ProcessException;
import com.wincornixdorf.platform.probase.util.process.ProcessRunner;
import com.wincornixdorf.platform.probase.util.process.ProcessRunnerFactory;
import com.wincornixdorf.platform.probase.util.process.StringReturningOutputHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

import static nuget.TestUtils.deleteFolder;

public class ProbaseCMDTest {

    private ProcessRunner mProcessRunner;
    private StringReturningOutputHandler outputStreamHandler;
    private StringReturningOutputHandler errorStreamHandler;


    private static final String RANDOM_NUGET_PACKAGE_NAME_EXAMPLE = "CSCW32.Core.RCHERR.ENG";
    private static final String OUTPUT_DIR = "c:\\tmp\\nuget\\output";
    @Before
    public void setupRunner() {
        mProcessRunner = ProcessRunnerFactory.createProcessRunner();
        mProcessRunner.setWorkingDir(new File(NugetAPIConstants.NUGET_PATH));
        outputStreamHandler = new StringReturningOutputHandler();
        errorStreamHandler = new StringReturningOutputHandler();

        mProcessRunner.setStdOutHandler(outputStreamHandler);
        mProcessRunner.setStdErrHandler(errorStreamHandler);
    }

    @Test
    public void testExecutingNugetInstallCommandUsingProbase() {
        String[] command = new String[]{
                "nuget.exe",
                "install",
                RANDOM_NUGET_PACKAGE_NAME_EXAMPLE,
                "-OutputDir",
                OUTPUT_DIR,
                "-ConfigFile",
                "c:\\nuget\\nuget.Config"
        };
        try {
            mProcessRunner.execute(command);
        } catch (ProcessException e) {
            e.printStackTrace();
        }
        File outputDirectory = new File(OUTPUT_DIR);
        File installedPackage[] = outputDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE);
            }
        });
        Assert.assertTrue("The test package (" + RANDOM_NUGET_PACKAGE_NAME_EXAMPLE + ") was not installed properly", installedPackage.length == 1);
    }

    @Test
    public void testCallingNugetListCommandUsingProbase() {
        String[] command = new String[]{
                "nuget.exe",
                "list",
                "-OutputDir",
                "c:\\tmp\\nuget\\output",
                "-ConfigFile",
                "c:\\nuget\\nuget.Config"
        };
        try {
            mProcessRunner.execute(command);
        } catch (ProcessException e) {
            e.printStackTrace();
        }
        System.out.println(errorStreamHandler.getOutput());
    }

    @After
    public void cleanTempFolderAfterTest(){
        File root = new File(NugetAPIConstants.NUGET_OUTPUT_DIR);
        File[] intsalledFolders = root.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE);
            }
        });
        if(intsalledFolders.length==1){
            deleteFolder(intsalledFolders[0]);
        }
    }
}
