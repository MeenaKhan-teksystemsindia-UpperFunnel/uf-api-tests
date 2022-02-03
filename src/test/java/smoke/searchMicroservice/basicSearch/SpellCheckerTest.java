package smoke.searchMicroservice.basicSearch;

import clients.search.post.searchBrand.SearchBrandClient;
import clients.search.post.searchBrand.SearchBrandRequest;
import clients.search.post.searchBrand.SearchBrandRequestBuilder;
import clients.search.post.searchBrand.response.SearchBrandResponseBody;
import entities.Sort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class SpellCheckerTest {

    @DataProvider(name = "MisspelledSearch")
    private static Object[][] getMisspelledSearch() {
        return new Object[][]{
                // misspelledTerm, correctedTerm
                {"Sirt", "shirt"},
                {"reabok shoes", "reebok shoes"},
                {"red smartphoone", "red smartphone"},
                {"mufti black sirt", "mufti black shirt"},
                {"women sweatshrt allen solly", "women sweatshirt allen solly"},
                {"black foot wear", "black footwear"},
                {"wood land men", "woodland men"}
                //{"addidas originals", "adidas originals"},
                //{"bluee shoes", "blue shoes"},
                //{"redd puma running shoes", "red puma running shoes"}
        };
    }

    @Test(dataProvider = "MisspelledSearch", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToSearchMisspelledTerm(String misspelledTerm, String correctedTerm) throws Exception {

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(misspelledTerm)
                .withSortOption(Sort.RELEVANCE)
                .inStock(true)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSpellCorrection(correctedTerm);
    }

    @DataProvider(name = "MisspelledSearchWithFilters")
    private static Object[][] getValidBrandName() {
        return new Object[][]{
                //searchTerm, brandName, brandCode, minPrice, maxPrice
                {"Sirt", "GAP","MBH11B11389", 2000, 3000}
        };
    }

    @Test(dataProvider = "MisspelledSearchWithFilters", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToApplyBrandAndPriceFilter(String searchTerm, String brandName, String brandCode, int minPrice, int maxPrice) {

        Sort relevance = Sort.RELEVANCE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(searchTerm)
                .withSortOption(relevance)
                .inStock(true)
                .withBrandCode(brandCode)
                .withPrice(minPrice, maxPrice)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSelectedSortOption(relevance.getSortedValue());
        responseBody.assertBrandName(brandName);
        responseBody.assertPrice(minPrice, maxPrice);
    }

    @Test(dataProvider = "MisspelledSearch", groups = {Categories.SMOKE, Categories.REGRESSION})
    public void shouldBeAbleToApplySortWithLowToHighPrice(String misspelledTerm, String correctedTerm) {

        Sort lowToHighPrice = Sort.LOW_TO_HIGH_PRICE;

        SearchBrandRequest request = new SearchBrandRequestBuilder()
                .withSearchTerm(misspelledTerm)
                .withSortOption(lowToHighPrice)
                .inStock(true)
                .isLuxuryProduct(false)
                .build();

        SearchBrandResponseBody responseBody = new SearchBrandClient().searchMicroserviceBrand(request);
        responseBody.assertTypeAndStatus();
        responseBody.assertSpellCorrection(correctedTerm);
        responseBody.assertLowToHighPriceResponse(lowToHighPrice.getSortedValue());
    }
}


