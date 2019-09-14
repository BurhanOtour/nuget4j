package nuget;

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
import java.util.Iterator;
import java.util.List;

import static nuget.TestUtils.deleteFolder;
import static org.junit.Assert.fail;

public class NugetCLITest {
    private static final String RANDOM_NUGET_PACKAGE_NAME_EXAMPLE = "CSCW32.Core.RCHERR.ENG";
    private static final String RANDOM_NUGET_PACKAGE_NAME_EXAMPLE_WITH_MOD = "CSCW32.3rdParty.7zr.exe";

    private static final NugetPackage PREDEFINED_NUGET_PACKAGE = new NugetPackage(
            "CSCW32.Printer.Tp24-2F.dll"
    );

    @Test
    public void testForExistenceOfNugetPackageDirectory() {
        NugetCLI nugetCLI = new NugetCLI(NugetAPIConstants.NUGET_CONFIG_FILE_PATH);
        try {
            File root = nugetCLI.install(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE);
            Assert.assertFalse(root == null);
        } catch (NugetAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testForExistanceOfDeliveryAndMODFiles() {
        NugetCLI nugetCLI = new NugetCLI(NugetAPIConstants.NUGET_CONFIG_FILE_PATH);
        try {
            InstalledNugetPackage installedNugetPackage = nugetCLI.install(PREDEFINED_NUGET_PACKAGE);
            Assert.assertTrue(installedNugetPackage.getDeliveryFiles().length > 0);

            String modFileName = installedNugetPackage.getModFile().getName();
            int extensionIndex = installedNugetPackage.getModFile().getName().lastIndexOf(".");
            Assert.assertTrue("No Delivery files were found", installedNugetPackage.getDeliveryFiles().length > 0);
            Assert.assertEquals("No mod file was found", "mod", modFileName.substring(extensionIndex + 1).toLowerCase());
        } catch (NugetAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testListOfNugetPackagesNotEmpty() {
        NugetCLI nugetCLI = new NugetCLI(NugetAPIConstants.NUGET_CONFIG_FILE_PATH);
        try {
            Iterator<NugetPackage> packageIterator = nugetCLI.list();
            while (packageIterator.hasNext()) {
                // A valid nuget package must contain at a minimum a name and a version
                NugetPackage nugetPackage = packageIterator.next();
                Assert.assertNotNull(nugetPackage.getPackageName());
                Assert.assertFalse(nugetPackage.getPackageName().equals(""));
            }
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
                return name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE)
                        || name.startsWith(RANDOM_NUGET_PACKAGE_NAME_EXAMPLE_WITH_MOD)
                        || name.startsWith(PREDEFINED_NUGET_PACKAGE.getPackageName());
            }
        });
        for (int i = 0; i < installedFolders.length; i++) {
            deleteFolder(installedFolders[i]);
        }
    }
}
