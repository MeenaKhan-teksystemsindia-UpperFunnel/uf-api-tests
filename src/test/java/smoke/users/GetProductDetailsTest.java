package smoke.users;

import clients.products.get.getProductDetails.GetProductDetailsClient;
import clients.products.get.getProductDetails.GetProductDetailsRequest;
import clients.products.get.getProductDetails.GetProductDetailsRequestBuilder;
import clients.products.get.getProductDetails.response.GetProductDetailsResponseBody;
import entities.Product;
import org.testng.annotations.Test;
import testData.productData.ProductDataService;
import util.Categories;

public class GetProductDetailsTest {

    @Test(groups = {Categories.SMOKE,Categories.PROD})
    public void shouldBeAbleToGetProductDetails() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        GetProductDetailsRequest request = new GetProductDetailsRequestBuilder()
                .withProductCode(product.getProductCode())
                .build();

        GetProductDetailsResponseBody responseBody = new GetProductDetailsClient().getProductDetails(request);
        responseBody.assertGetProductDetailsResponse();
    }
}
