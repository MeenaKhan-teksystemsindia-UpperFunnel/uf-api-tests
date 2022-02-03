package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.getCarts.GetCartsClient;
import clients.carts.get.getCarts.GetCartsRequest;
import clients.carts.get.getCarts.GetCartsRequestBuilder;
import clients.carts.get.getCarts.GetCartsResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetCartTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGetCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        new StartingStep(useCase)
                .proceedTillGenerateCartStep()
                .generateCart();

        GetCartsRequest request = new GetCartsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        GetCartsResponseBody responseBody = new GetCartsClient().getCarts(request);
        responseBody.assertGetCartsResponse();
    }
}
