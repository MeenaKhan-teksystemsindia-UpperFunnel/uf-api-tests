package smoke.searchMicroservice;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SearchBrandAndCategoryTest {

    @DataProvider(name = "ValidCategoryBrandNames")
    private static Object[][] getValidBrandCategoryNames() {
        return new Object[][]{
                // BrandName, CategoryName
                {"Soch", "Saree"},
                {"Bombay Paisley", "dress"},
                {"Arrow", "Shirt"},
                {"Story@Home", "Curtains"},
                {"Xiaomi", "smartphone"},
                {"Puma", "shirt"}
        };
    }

    @Test(dataProvider = "ValidCategoryBrandNames", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchByCategory1(String brandName, String categoryName) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(brandName + " " + categoryName)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSearchBrandResponse(brandName);
        responseBody.assertBrandFilter(brandName);
        responseBody.assertSearchCategoryName(categoryName);
    }

}
