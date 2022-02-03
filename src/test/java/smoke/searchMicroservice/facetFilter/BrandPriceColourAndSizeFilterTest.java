package smoke.searchMicroservice.facetFilter;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Colour;
import entities.Size;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class BrandPriceColourAndSizeFilterTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName, categoryCode, brandCode, minPrice, maxPrice
                {"Varanga", "MSH10", "MBH11A00438", 500, 2000},
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToApplyBrandPriceColourAndSizeFilter(String brandName, String categoryCode, String brandCode, int minPrice, int maxPrice) {

        Sort relevance = Sort.RELEVANCE;
        Colour colour = Colour.BLACK;
        Size size = Size.L;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(Sort.RELEVANCE)
                .withCategoryCode(categoryCode)
                .withBrandCode(brandCode)
                .inStock(true)
                .isLuxuryProduct(false)
                .withColour(colour)
                .withPrice(minPrice, maxPrice)
                .withSize(size)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(relevance.getSortedValue());
        responseBody.assertCategoryCode(categoryCode);
        responseBody.assertBrandName(brandName);
        responseBody.assertColour(colour);
        responseBody.assertSize(size);
        responseBody.assertPrice(minPrice, maxPrice);
    }
}