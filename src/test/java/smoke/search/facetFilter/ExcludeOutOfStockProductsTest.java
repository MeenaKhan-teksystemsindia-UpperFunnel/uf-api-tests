package smoke.search.facetFilter;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class ExcludeOutOfStockProductsTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                // brandName, brandCode
                {"Whirlpool", "MBH12E00047"},
                {"Blue Star", "MBH12E00068"},
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToExcludeOutOfStockProducts(String brandName, String brandCode) {

        Sort relevance = Sort.RELEVANCE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(relevance)
                .withBrandCode(brandCode)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(relevance.getSortedValue());
        responseBody.assertBrandName(brandName);
        responseBody.assertExcludeOutOfStockProducts();
    }
}