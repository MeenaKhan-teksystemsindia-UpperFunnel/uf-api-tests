package smoke.product;

import clients.products.get.manufacturingDetails.ManufacturingDetailsClient;
import clients.products.get.manufacturingDetails.ManufacturingDetailsRequest;
import clients.products.get.manufacturingDetails.ManufacturingDetailsRequestBuilder;
import clients.products.get.manufacturingDetails.response.ManufacturingDetailsResponseBody;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Categories;

public class ManufacturingDetailsTest {

    @DataProvider(name = "ValidCategoryAndBrand")
    private static Object[][] getValidCategoryAndBrand() {
        return new Object[][]{
                // categoryCode, brandCode
                {"MSH1216106", "MBH12E00015"},
                {"MSH1216107", "E00970"},
        };
    }

    @Test(dataProvider = "ValidCategoryAndBrand", groups = {Categories.TODO, Categories.PROD})
    public void shouldBeAbleToGetManufacturingDetails(String categoryCode, String brandCode) {

        ManufacturingDetailsRequest request = new ManufacturingDetailsRequestBuilder()
                .withCategory(categoryCode)
                .withBrand(brandCode)
                .build();

        ManufacturingDetailsResponseBody responseBody = new ManufacturingDetailsClient().getManufacturingDetails(request);

        responseBody.assertManufacturingDetailsResponse(categoryCode);

    }
}