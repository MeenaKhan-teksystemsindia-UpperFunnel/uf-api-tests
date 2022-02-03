package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.bagCount.BagCountClient;
import clients.carts.get.bagCount.BagCountRequest;
import clients.carts.get.bagCount.BagCountRequestBuilder;
import clients.carts.get.bagCount.response.BagCountResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class BagCountTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetBagCount() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase)
                .proceedTillAddToCartStep()
                .addOneItemToCart();

        BagCountRequest request = new BagCountRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .withCardId(useCase.getCartGuid())
                .build();

        BagCountResponseBody responseBody = new BagCountClient().getBagCount(request);

        responseBody.assertGetBagCountResponse("1");

    }
}
