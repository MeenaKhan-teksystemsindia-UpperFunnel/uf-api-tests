package smoke.searchMicroservice.sorting;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SortByLowToHighPriceTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName
                {"Samsung"},
                {"Soch"}
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToApplySortWithLowToHighPrice(String brandName) {

        Sort lowToHighPrice = Sort.LOW_TO_HIGH_PRICE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(brandName)
                .withSortOption(lowToHighPrice)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertBrandName(brandName);
        responseBody.assertLowToHighPriceResponse(lowToHighPrice.getSortedValue());
    }
}