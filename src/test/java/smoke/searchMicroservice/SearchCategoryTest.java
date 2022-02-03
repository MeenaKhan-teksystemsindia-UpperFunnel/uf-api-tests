package smoke.searchMicroservice;

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
    private static Object[][] getValidCategoryNames() {
        return new Object[][]{
                // categoryName, categoryCode
                {"Footwear", "MSH13"},
                {"Women Ethnic wear", "MSH1012"},
                {"Air conditioner", "MSH1230"},
                {"Bed linen", "MSH2213"},
                {"Men sneakers", "MSH1311115"},
                {"bathrobes", "MSH2212101"},
                {"Side by side refrigerators", "MSH1214100100"},
                {"Saree", "MSH1012102,MSH2111104"} //Category codes for Women and Kids departments
        };
    }

    @Test(dataProvider = "ValidCategoryTypes", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchByCategory(String categoryName, String categoryCodes) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(categoryName)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertCategoryCodes(categoryCodes);
        responseBody.assertCategoryCodeInResults(categoryCodes);

    }

}