package unit;

import org.testng.Assert;
import org.testng.annotations.Test;
import properties.Properties;
import testbase.TestBase;
import util.Categories;

public class PropertiesReaderTests extends TestBase {

    @Test(groups = Categories.UNIT)
    public void shouldBeAbleToReadProperties() {

        String loginUrl = Properties.oauthTokenUrl;

        Assert.assertTrue(loginUrl.startsWith("https"));
    }
}
