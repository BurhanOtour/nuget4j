package nuget.api;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;
import me.burhanotour.tools.nuget4j.nuget.api.NugetInstallAPI;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class NugetInstallAPITest {

    private static final String RANDOM_NUGET_PACKAGE_NAME_EXAMPLE = "CSCW32.Core.RCHERR.ENG";
    private static final String RANDOM_NUGET_PACKAGE_NAME_EXAMPLE_WITH_MOD = "CSCW32.3rdParty.7zr.exe";

    @Test
    public void testDeliverFileExistsAfterInstallation() {
        NugetInstallAPI api = new NugetInstallAPI(NugetAPIConstants.NUGET_OUTPUT_DIR, NugetAPIConstants.NUGET_CONFIG_FILE_PATH);

        try {
            api.install(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE);
            // In the output directory we should find the installed nuget package
            File outputDirectory = new File(NugetAPIConstants.NUGET_OUTPUT_DIR);
            File installedPackage[] = outputDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE);
                }
            });
            Assert.assertTrue("The test package (" + RANDOM_NUGET_PACKAGE_NAME_EXAMPLE + ") was not installed properly", installedPackage.length == 1);
        } catch (NugetAPIException e) {
            Assert.fail("No package was installed");
        }
    }

    @After
    public void cleanTempFolderAfterTest() throws IOException {
        File root = new File(NugetAPIConstants.NUGET_OUTPUT_DIR);

        File[] installedFolders = root.listFiles((File dir, String name) ->
                name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE)
        );
        if (installedFolders.length == 1) {
            FileUtils.deleteDirectory(installedFolders[0]);
        }
    }

}
