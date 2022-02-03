package smoke.searchMicroservice;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

@Test(groups = {Categories.SMOKE, Categories.PROD})
public class SearchBrandTest {

    @DataProvider(name = "ValidBrandNames")
    private static Object[][] getValidBrandNames() {
        return new Object[][]{
                // brandName
                {"Whirlpool"},
                {"Blue Star"},
                {"Sony"},
                {"Carrier"},
                {"Lloyd"},
                {"Puma"},
                {"Reebok"},
                {"Soch"}
        };
    }
//TEST TEST
    @Test(dataProvider = "ValidBrandNames", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchBrand(String brandName) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(brandName)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSearchBrandResponse(brandName);
        responseBody.assertBrandFilter(brandName);
    }
}

