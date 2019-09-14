package me.burhanotour.tools.nuget4j.nuget.entities;

public class NugetPackage {
    private final String packageName;

    public NugetPackage(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return getPackageName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof NugetPackage))
            return false;
        NugetPackage nugetPackage = (NugetPackage) obj;
        return (packageName == null ? nugetPackage.getPackageName() == null : packageName.equals(nugetPackage.getPackageName()));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        return result;
    }
}
