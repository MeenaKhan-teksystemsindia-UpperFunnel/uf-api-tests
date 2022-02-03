package smoke.search;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

@Test(groups = {Categories.SMOKE, Categories.PROD})
public class SearchBrandTest {

    @DataProvider(name = "ValidBrandNames")
    private static Object[][] getValidBrandNames() {
        return new Object[][]{
                // brandName, brandCode
                {"Whirlpool", "MBH12E00047"},
                {"Blue Star", "MBH12E00068"},
                {"Sony", "MBH12E00002"},
                {"Carrier", "MBH12B10047"},
                {"Lloyd", "MBH12E00882"}
        };
    }

    @Test(dataProvider = "ValidBrandNames", groups = Categories.SMOKE)
    public void shouldBeAbleToSearchBrand(String brandName, String brandCode) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSortOption(Sort.RELEVANCE)
                .withBrandCode(brandCode)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchBrand(request);
        responseBody.assertSearchBrandResponse(brandName);
    }
}
