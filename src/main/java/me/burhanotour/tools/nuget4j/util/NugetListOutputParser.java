package me.burhanotour.tools.nuget4j.util;

import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NugetListOutputParser {
    public static List<NugetPackage> parse(File in) throws IOException {
        List<NugetPackage> packages = new ArrayList();
        List<String> lines = FileUtils.readLines(in, "UTF-8");
        for (String line : lines) {
            if(line.equalsIgnoreCase(lines.get(0))){
                continue;
            }
            String currentLine = line.trim();
            String packageName = currentLine.substring(0,currentLine.indexOf(' '));
            packages.add(new NugetPackage(packageName));
        }
        return packages;
    }
}
