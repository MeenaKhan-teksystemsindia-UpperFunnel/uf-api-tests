package smoke.searchMicroservice.basicSearch;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class StemmerAndSpacerTest {

    @DataProvider(name = "CategoryNames")
    private static Object[][] getCategoryNames() {
        return new Object[][]{
                // category1, category2
                {"Shirt", "shirts"},
                {"Dress", "Dresses"},
                {"Smartwatch", "Smartwatches"},
                {"saree", "sarees"},
                {"smartphone", "smartphones"},
                {"smart watch", "smartwatch"}
                //{"smart phone", "smartphone"}
        };
    }

    @Test(dataProvider = "CategoryNames", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void verifyStemmer(String category1, String category2) throws Exception {

        SearchBrandRequest request1 = new SearchBrandRequestBuilder()
                .withSearchTerm(category1)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request1);

        SearchBrandRequest request2 = new SearchBrandRequestBuilder()
                .withSearchTerm(category2)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody1 = new SearchBrandClient().searchMicroserviceBrand(request2);
        responseBody.assertTypeAndStatus();
        responseBody.assertProductsCount(responseBody1);

    }

    @Test(groups = {Categories.SMOKE, Categories.REGRESSION})
    public void verifyPagination() throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm("shirt")
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertProductsCountInAPage(request.getPageSize());

    }

    @Test(groups = {Categories.SMOKE, Categories.REGRESSION})
    public void verifyInvalidSearch() throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm("fff1111")
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertNoResponse();

    }



}
