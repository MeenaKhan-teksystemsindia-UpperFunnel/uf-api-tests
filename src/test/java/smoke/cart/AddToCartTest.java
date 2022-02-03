package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.post.addToCart.response.AddToCartResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class AddToCartTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToAddItemToCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        AddToCartResponseBody responseBody = new StartingStep(useCase)
                .proceedTillAddToCartStep()
                .addOneItemToCart();

        responseBody.assertAddToCartResponse(useCase);
    }
}
