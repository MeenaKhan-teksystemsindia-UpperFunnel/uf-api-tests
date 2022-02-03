package smoke.searchMicroservice;

import clients.search.get.plpBanners.PlpBannersClient;
import clients.search.get.plpBanners.PlpBannersRequest;
import clients.search.get.plpBanners.PlpBannersRequestBuilder;
import clients.search.get.plpBanners.PlpBannersResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class PlpBannersTest {

    @Test(groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleGetPlpBanners() {

        String categoryCode = "MSH1112102";

        PlpBannersRequest request = new PlpBannersRequestBuilder()
                .withCategoryCode(categoryCode)
                .build();

        PlpBannersResponseBody responseBody = new PlpBannersClient().getPlpBanners(request);
        responseBody.assertPlpBannersResponse();
    }
}