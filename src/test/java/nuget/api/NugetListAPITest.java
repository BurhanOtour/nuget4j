package nuget.api;

import me.burhanotour.tools.nuget4j.exceptions.NugetAPIException;
import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;
import me.burhanotour.tools.nuget4j.nuget.api.NugetListAPI;
import me.burhanotour.tools.nuget4j.nuget.entities.NugetPackage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class NugetListAPITest {

    @Test
    public void testWithWrongConfigInformation() throws NugetAPIException {
        NugetListAPI api = new NugetListAPI("nuget_wrong.config");
        Iterator<NugetPackage> nugetPackageIterator = api.list();
        Assert.assertFalse(nugetPackageIterator.hasNext());
    }

    @Test
    public void testNonEmptyPackageListReturned() {
        NugetListAPI api = new NugetListAPI(NugetAPIConstants.NUGET_CONFIG_FILE_PATH);
        try {
            Iterator<NugetPackage> nugetPackageIterator = api.list();
            Assert.assertTrue(nugetPackageIterator.hasNext());
        } catch (NugetAPIException e) {
            Assert.fail("The command \"nuget.exe list\" failed to execute");
        }
    }
}
