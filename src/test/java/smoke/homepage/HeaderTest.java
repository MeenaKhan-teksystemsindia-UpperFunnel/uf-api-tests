package smoke.homepage;

import clients.catalogs.get.headerClient.HeaderClient;
import clients.catalogs.get.headerClient.response.HeaderResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class HeaderTest {

    @Test(groups = {Categories.SMOKE})
    public void shouldBeAbleReturnHeaders() {

        HeaderResponseBody responseBody = new HeaderClient().getHeaders();
        responseBody.assertHeader();
    }
}
