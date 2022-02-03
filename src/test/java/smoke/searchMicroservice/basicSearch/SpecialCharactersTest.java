package smoke.searchMicroservice.basicSearch;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SpecialCharactersTest {

    @DataProvider(name = "SearchWithSpecialCharacter")
    private static Object[][] getSearchTerms() {
        return new Object[][]{
                // Search Text
                {"Black & Decker"},
                {"T-shirt"},
                {"Story@Home"},
                {"Levi's"},
                {"U.S. Polo"}
        };
    }

    @Test(dataProvider = "SearchWithSpecialCharacter", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchBrand(String searchText) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(searchText)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSearchTextInProductTitle(searchText);

    }
}

