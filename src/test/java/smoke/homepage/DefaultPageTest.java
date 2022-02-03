package smoke.homepage;

import clients.cms.get.defaultpage.DefaultPageClient;
import clients.cms.get.defaultpage.DefaultPageRequest;
import clients.cms.get.defaultpage.DefaultPageRequestBuilder;
import clients.cms.get.defaultpage.response.DefaultPageResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class DefaultPageTest {

    @Test(groups = {Categories.SMOKE})
    public void shouldBeAbleDisplayDefaultPage() {

        DefaultPageRequest request = new DefaultPageRequestBuilder().build();

        DefaultPageResponseBody responseBody = new DefaultPageClient().getDefaultPage(request);

        responseBody.assertDefaultPageResponse();

    }
}