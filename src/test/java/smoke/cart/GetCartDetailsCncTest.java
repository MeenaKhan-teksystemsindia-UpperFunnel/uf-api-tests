package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.cartDetailsCnc.response.CartDetailsCncResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetCartDetailsCncTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetCartDetailsCnc() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        CartDetailsCncResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetCartDetailsCncStep()
                .getCartDetailsCnc();

        responseBody.assertGetCartDetailsResponse(useCase);
    }
}
