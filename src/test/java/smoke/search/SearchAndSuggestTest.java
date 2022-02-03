package smoke.search;

import clients.search.get.searchAndSuggest.SearchAndSuggestClient;
import clients.search.get.searchAndSuggest.SearchAndSuggestRequest;
import clients.search.get.searchAndSuggest.SearchAndSuggestRequestBuilder;
import clients.search.get.searchAndSuggest.response.SearchAndSuggestResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class SearchAndSuggestTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToSearchAndSuggest() {

        String searchString = "Voltas";
        SearchAndSuggestRequest request = new SearchAndSuggestRequestBuilder()
                .withSearchString(searchString)
                .build();

        SearchAndSuggestResponseBody responseBody = new SearchAndSuggestClient().searchAndSuggest(request);

        responseBody.assertSearchAndSuggestResponse();

    }
}