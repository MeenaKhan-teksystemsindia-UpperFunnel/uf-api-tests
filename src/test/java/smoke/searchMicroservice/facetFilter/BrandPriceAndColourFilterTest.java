package smoke.searchMicroservice.facetFilter;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Colour;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class BrandPriceAndColourFilterTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName, brandCode, minPrice, maxPrice
                {"Samsung", "MBH12E00016", 20000, 30000},
                {"Voltas", "MBH12E00001", 30000, 40000},
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToApplyBrandPriceAndColourFilter(String brandName, String brandCode, int minPrice, int maxPrice) {

        Sort relevance = Sort.RELEVANCE;
        Colour colour = Colour.WHITE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(relevance)
                .withBrandCode(brandCode)
                .inStock(true)
                .isLuxuryProduct(false)
                .withColour(colour)
                .withPrice(minPrice, maxPrice)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(relevance.getSortedValue());
        responseBody.assertBrandName(brandName);
        responseBody.assertColour(colour);
        responseBody.assertPrice(minPrice, maxPrice);
    }
}