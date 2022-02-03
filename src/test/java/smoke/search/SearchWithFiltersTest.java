package smoke.search;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class SearchWithFiltersTest {

    @Test(groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleSearchWithFilters() {

        String CategoryCode = "MSH1112102";

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withBrandcodeCostAndSize(CategoryCode)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertSearchCategoryWithPriceResponse(CategoryCode);
    }
}
