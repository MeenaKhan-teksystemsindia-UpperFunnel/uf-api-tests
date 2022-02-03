package smoke.search.sorting;

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
                //brandName, brandCode
                {"Samsung", "MBH12E00016"},
                {"Voltas", "MBH12E00001"},
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToSortByBrandNewProducts(String brandName, String brandCode) {

        Sort brandNew = Sort.BRAND_NEW;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(brandNew)
                .withBrandCode(brandCode)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertBrandName(brandName);
        responseBody.assertSelectedSortOption(brandNew.getSortedValue());
    }
}