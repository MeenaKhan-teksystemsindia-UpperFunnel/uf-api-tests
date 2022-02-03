package smoke.searchMicroservice.basicSearch;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.Test;
import properties.SystemProperties;
import util.Categories;

public class SearchListingIdTest {

    String listingId = "";
    String env = SystemProperties.ENV;

    @Test(groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchBrand() throws Exception {

        if(env.equals("awsqa2")){
            listingId = "mp000000005062000";
        }
        else if(env.equals("preprod2")){
            listingId = "mp000000006389415";
        }

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(listingId)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSearchListingId(listingId);

    }
}
