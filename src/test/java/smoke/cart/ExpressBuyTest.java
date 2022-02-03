package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.expressBuy.ExpressBuyClient;
import clients.carts.get.expressBuy.ExpressBuyRequest;
import clients.carts.get.expressBuy.ExpressBuyRequestBuilder;
import clients.carts.get.expressBuy.response.ExpressBuyResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class ExpressBuyTest {

    @Test(groups = Categories.SMOKE)
    public void shouldAddProductToCartUsingBuyNowOption() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        ExpressBuyRequest request = new ExpressBuyRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withProductCode(product.getProductCode())
                .withUssidCode(product.getUssid()).build();

        ExpressBuyResponseBody expressBuyResponseBody = new ExpressBuyClient().expressBuy(request);
        expressBuyResponseBody.assertExpressBuyResponse();

    }
}
