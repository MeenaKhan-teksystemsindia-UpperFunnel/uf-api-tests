package smoke.product;

import clients.products.get.getProductInfo.GetProductInfoClient;
import clients.products.get.getProductInfo.GetProductInfoRequest;
import clients.products.get.getProductInfo.GetProductInfoRequestBuilder;
import clients.products.get.getProductInfo.response.GetProductInfoResponseBody;
import entities.Product;
import org.testng.annotations.Test;
import testData.productData.ProductDataService;
import util.Categories;

public class GetProductInfoTest {

    @Test(groups = {Categories.SMOKE,Categories.PROD})
    public void ShouldBeAbleToGetProductInfo() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        GetProductInfoRequest request = new GetProductInfoRequestBuilder()
                .withProductCode(product.getProductCode())
                .build();

        GetProductInfoResponseBody responseBody = new GetProductInfoClient().getProductInfo(request);

        responseBody.assertGetProductInfoResponse(product);

    }
}