package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.deliverymode.response.SelectDeliveryModeForOrderResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class SelectDeliveryModeForOrderTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToSelectDeliveryModeForOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .build();

        SelectDeliveryModeForOrderResponseBody responseBody = new StartingStep(useCase)
                .proceedTillSelectDeliveryModeStep()
                .selectDeliveryMode();

        responseBody.assertSelectDeliveryModeForOrderResponse();
    }
}