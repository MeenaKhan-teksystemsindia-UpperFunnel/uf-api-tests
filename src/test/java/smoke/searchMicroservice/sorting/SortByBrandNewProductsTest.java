package smoke.searchMicroservice.sorting;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SortByBrandNewProductsTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //SearchTerm
                {"shirt"},
                {"ac"}
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSortByBrandNewProducts(String searchTerm) {

        Sort brandNew = Sort.BRAND_NEW;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(searchTerm)
                .withSortOption(brandNew)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(brandNew.getSortedValue());
        responseBody.assertSortByNew();
    }
}