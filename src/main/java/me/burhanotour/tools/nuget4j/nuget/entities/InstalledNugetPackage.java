package me.burhanotour.tools.nuget4j.nuget.entities;

import java.io.File;
import java.util.Arrays;

public class InstalledNugetPackage {
    private NugetPackage nugetPackage;
    private File[] deliveryFiles;
    private File modFile;
    private File rootDirectory;

    public InstalledNugetPackage(NugetPackage nugetPackage, File[] deliveryFiles, File modFile, File rootDirectory) {
        this.nugetPackage = nugetPackage;
        this.deliveryFiles = deliveryFiles;
        this.rootDirectory = rootDirectory;
        this.modFile = modFile;
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public File[] getDeliveryFiles() {
        return deliveryFiles;
    }

    public NugetPackage getNugetPackage() {
        return nugetPackage;
    }

    public File getModFile() {
        return modFile;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InstalledNugetPackage installedNugetPackage = (InstalledNugetPackage) obj;
        return (nugetPackage == null ? installedNugetPackage.getNugetPackage() == null : nugetPackage.equals(installedNugetPackage.getNugetPackage())) &&
                (modFile == null ? installedNugetPackage.getModFile() == null : modFile.equals(installedNugetPackage.getModFile())) &&
                (rootDirectory == null ? installedNugetPackage.getRootDirectory() == null : rootDirectory.equals(installedNugetPackage.getRootDirectory())) &&
                (deliveryFiles == null ? installedNugetPackage.getDeliveryFiles() == null : Arrays.equals(deliveryFiles, installedNugetPackage.getDeliveryFiles()));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + nugetPackage.hashCode();
        result = prime * result + modFile.hashCode();
        result = prime * result + rootDirectory.hashCode();
        result = prime * result + Arrays.hashCode(deliveryFiles);
        return result;
    }
}
