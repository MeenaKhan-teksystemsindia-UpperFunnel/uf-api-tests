package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.deleteEntries.RemoveProductFromCartClient;
import clients.carts.get.deleteEntries.RemoveProductFromCartRequest;
import clients.carts.get.deleteEntries.RemoveProductFromCartRequestBuilder;
import clients.carts.get.deleteEntries.responseBody.RemoveProductFromCartResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class DeleteEntriesTest {

    @Test(groups = Categories.SMOKE)
    public void shouldRemoveProductFromCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        new StartingStep(useCase).proceedTillAddToCartStep().addOneItemToCart();

        RemoveProductFromCartRequest request = new RemoveProductFromCartRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .withProductIndex(0)
                .build();

        RemoveProductFromCartResponseBody removeProductFromCartResponseBody = new RemoveProductFromCartClient().deleteEntries(request);
        removeProductFromCartResponseBody.assertDeleteEntriesResponse();
    }
}
