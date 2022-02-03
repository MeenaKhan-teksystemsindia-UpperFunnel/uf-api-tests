package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.addAddressToOrder.response.AddAddressToOrderResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class AddAddressToOrderTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToAddAddressToOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address).build();

        AddAddressToOrderResponseBody responseBody = new StartingStep(useCase)
                .proceedTillAddAddressToOrderStep()
                .addAddressToOrder();

        responseBody.assertAddAddressToOrderResponse();
    }
}
