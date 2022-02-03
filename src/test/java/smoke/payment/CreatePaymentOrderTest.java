package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.paymentOrder.createPaymentOrder.response.CreatePaymentOrderResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class CreatePaymentOrderTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCreatePaymentOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        CreatePaymentOrderResponseBody responseBody = new StartingStep(useCase)
                .proceedTillCreatePaymentOrderStep()
                .createPaymentOrder();

        responseBody.assertCreatePaymentOrderResponse();
    }
}
