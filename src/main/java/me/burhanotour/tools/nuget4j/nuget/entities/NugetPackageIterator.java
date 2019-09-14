package me.burhanotour.tools.nuget4j.nuget.entities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class NugetPackageIterator implements Iterator<NugetPackage> {

    private final LineIterator iterator;

    public NugetPackageIterator(File input) throws IOException {
        this.iterator = FileUtils.lineIterator(input, "UTF-8");
        // Skip the first line
        if(this.iterator.hasNext()){
            this.iterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public NugetPackage next() {
        String fullNugetPackageName = this.iterator.next();
        String packageName = fullNugetPackageName.substring(0,fullNugetPackageName.indexOf(' '));
        return new NugetPackage(packageName);
    }
}
