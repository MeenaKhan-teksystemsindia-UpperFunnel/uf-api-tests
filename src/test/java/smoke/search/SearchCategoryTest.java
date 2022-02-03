package smoke.search;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SearchCategoryTest {

    @DataProvider(name = "ValidCategoryTypes")
    private static Object[][] getValidBrandNames() {
        return new Object[][]{
                // categoryName, categoryCode
                {"Footwear", "MSH1311113"},
        };
    }

    @Test(dataProvider = "ValidCategoryTypes", groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToSearchByCategory(String categoryName, String categoryCode) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(Sort.RELEVANCE)
                .withCategoryCode(categoryCode)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertSearchCategoryResponse(categoryName);
    }
}
