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

public class BrandColourAndSizeFilterTest {

    @DataProvider(name = "ValidBrandName")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //brandName, categoryCode, brandCode
                {"Varanga", "MSH10", "MBH11A00438"},
        };
    }

    @Test(dataProvider = "ValidBrandName", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToApplyBrandColourAndSizeFilter(String brandName, String categoryCode, String brandCode) {

        Sort relevance = Sort.RELEVANCE;
        Colour colour = Colour.BLACK;
        Size size = Size.L;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(relevance)
                .withCategoryCode(categoryCode)
                .withBrandCode(brandCode)
                .inStock(true)
                .isLuxuryProduct(false)
                .withColour(colour)
                .withSize(size)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(relevance.getSortedValue());
        responseBody.assertCategoryCode(categoryCode);
        responseBody.assertBrandName(brandName);
        responseBody.assertColour(colour);
        responseBody.assertSize(size);
    }
}