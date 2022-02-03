package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.paymentModes.GetPaymentModesClient;
import clients.payment.post.paymentModes.GetPaymentModesRequest;
import clients.payment.post.paymentModes.GetPaymentModesRequestBuilder;
import clients.payment.post.paymentModes.response.GetPaymentModesResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetPaymentModesTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetPaymentModes() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase)
                .proceedTillDisplayOrderSummaryStep().displayOrderSummary();

        GetPaymentModesRequest request = new GetPaymentModesRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withCartGuid(useCase.getCartGuid())
                .withUsername(useCase.getUser().getUsername())
                .build();

        GetPaymentModesResponseBody responseBody = new GetPaymentModesClient().getPaymentModes(request);
        responseBody.assertGetPaymentModesResponse();
    }
}
