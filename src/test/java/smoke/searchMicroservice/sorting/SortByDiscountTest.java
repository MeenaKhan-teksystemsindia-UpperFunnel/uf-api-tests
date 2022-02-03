package smoke.searchMicroservice.sorting;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SortByDiscountTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName
                {"Samsung"},
                {"soch"}
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSortByDiscountPrice(String brandName) {

        Sort discounts = Sort.DISCOUNTS;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(brandName)
                .withSortOption(discounts)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertBrandName(brandName);
        responseBody.assertSortByDiscountResponse(discounts.getSortedValue());
    }
}