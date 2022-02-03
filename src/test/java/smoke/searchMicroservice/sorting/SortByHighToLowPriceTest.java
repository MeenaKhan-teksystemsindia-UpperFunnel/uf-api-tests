package smoke.searchMicroservice.sorting;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SortByHighToLowPriceTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName
                {"Samsung"},
                {"soch"}
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToApplySortWithHighToLowPrice(String brandName) {

        Sort highToLowPrice = Sort.HIGH_TO_LOW_PRICE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(brandName)
                .withSortOption(highToLowPrice)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertBrandName(brandName);
        responseBody.assertHighToLowPriceResponse(highToLowPrice.getSortedValue());
    }
}