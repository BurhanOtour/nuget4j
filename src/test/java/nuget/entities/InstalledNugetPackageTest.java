package nuget.entities;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.cli.NugetCLI;
import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;
import me.burhanotour.tools.nuget4j.nuget.entities.InstalledNugetPackage;
import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

import static nuget.TestUtils.deleteFolder;
import static org.junit.Assert.fail;

public class InstalledNugetPackageTest {
    private static final NugetPackage PREDEFINED_NUGET_PACKAGE = new NugetPackage(
            "CSCW32.Printer.Tp24-2F.dll"
    );

    @Test
    public void testEqualsForNugetPackage() {
        NugetPackage firstNugetPackage = new NugetPackage("First_name");
        NugetPackage secondNugetPackage = new NugetPackage("First_name");
        Assert.assertEquals(firstNugetPackage, secondNugetPackage);
    }

    @Test
    public void testEqualsForInstalledNugetPackage() {
        NugetCLI nugetCLI = new NugetCLI(NugetAPIConstants.NUGET_CONFIG_FILE_PATH);
        try {
            InstalledNugetPackage installedNugetPackage1 = nugetCLI.install(PREDEFINED_NUGET_PACKAGE);
            InstalledNugetPackage installedNugetPackage2 = nugetCLI.install(PREDEFINED_NUGET_PACKAGE);
            Assert.assertEquals(installedNugetPackage1, installedNugetPackage2);
        } catch (NugetAPIException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void cleanTempFolderAfterTest() {
        File root = new File(NugetAPIConstants.NUGET_OUTPUT_DIR);
        File[] installedFolders = root.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(PREDEFINED_NUGET_PACKAGE.getPackageName());
            }
        });
        for (int i = 0; i < installedFolders.length; i++) {
            deleteFolder(installedFolders[i]);
        }
    }
}
