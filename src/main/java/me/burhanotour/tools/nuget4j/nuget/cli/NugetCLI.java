package me.burhanotour.tools.nuget4j.nuget.cli;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;
import me.burhanotour.tools.nuget4j.nuget.api.NugetInstallAPI;
import me.burhanotour.tools.nuget4j.nuget.api.NugetListAPI;
import me.burhanotour.tools.nuget4j.nuget.entities.InstalledNugetPackage;
import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;

public class NugetCLI {

    private final static Logger LOGGER = Logger.getLogger(NugetCLI.class);

    private final String mOutputDir = NugetAPIConstants.NUGET_OUTPUT_DIR;

    private final String configFileDir;

    private final NugetInstallAPI mInstallAPI;

    private final NugetListAPI mListAPI;

    public NugetCLI() {
        super();
        configFileDir = NugetAPIConstants.NUGET_CONFIG_FILE_PATH;
        mInstallAPI = new NugetInstallAPI(mOutputDir, configFileDir);
        mListAPI = new NugetListAPI(configFileDir);
    }

    public NugetCLI(String pathToConfigFile) {
        configFileDir = pathToConfigFile;
        mInstallAPI = new NugetInstallAPI(mOutputDir, configFileDir);
        mListAPI = new NugetListAPI(configFileDir);
    }

    public File install(final String nugetPackageName) throws NugetAPIException {
        // @TODO enhance the code, make use of the retVals (or delete them later)
        int retVal = Integer.MIN_VALUE;
        try {
            retVal = mInstallAPI.install(nugetPackageName);
        } catch (NugetAPIException e) {
            throw e;
        }
        File outputDirectory = new File(mOutputDir);
        File[] dirs = outputDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(nugetPackageName);
            }
        });
        if (dirs.length != 1 || !dirs[0].isDirectory()) {
            throw new IllegalStateException("The NuGet Package " + nugetPackageName + " was not installed properly");
        }

        return dirs[0];
    }

    public InstalledNugetPackage install(final NugetPackage nugetPackage) throws NugetAPIException {
        LOGGER.info("Start installing NuGet Package: " + nugetPackage);

        // Installed the nuget package
        File installedNugetPackageRootDirectory = this.install(nugetPackage.getPackageName());

        // Construct the InstalledNugetPackage object
        File deliveryDirectory = new File(installedNugetPackageRootDirectory.getAbsolutePath() + "\\DlV");
        File docDirectory = new File(installedNugetPackageRootDirectory.getAbsolutePath() + "\\Doc");
        File[] filesInDlv = deliveryDirectory.listFiles() != null ? deliveryDirectory.listFiles() : new File[]{};
        File[] modFiles = docDirectory.listFiles();
        File modFile = null;
        if (modFiles != null) {
            /**
             * @TODO Check the limitation Doc item. 1
             */
            if (modFiles.length == 1 && (modFiles[0].getName().endsWith(".mod") || modFiles[0].getName().endsWith(".MOD"))) {
                modFile = modFiles[0];
            }
        }
        InstalledNugetPackage installedNugetPackage = new InstalledNugetPackage(nugetPackage, filesInDlv, modFile, installedNugetPackageRootDirectory);
        for (File fileInDlv : installedNugetPackage.getDeliveryFiles()) {
            LOGGER.info("The installed delivery file: " + fileInDlv.getAbsolutePath());
        }
        return installedNugetPackage;
    }

    public Iterator<NugetPackage> list() throws NugetAPIException {
        LOGGER.info("Listing Nuget Packages");
        try {
            Iterator<NugetPackage> nugetPackageIterator = mListAPI.list();
            return nugetPackageIterator;
        } catch (NugetAPIException e) {
            throw e;
        }
    }
}
