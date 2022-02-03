package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.codEligibility.response.CodEligibilityResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.CodEligibilityStep;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class CodEligibilityTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCodEligibilityCheckForOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase).proceedTillGetCartDetailsCncStep().getCartDetailsCnc();

        CodEligibilityResponseBody responseBody = new CodEligibilityStep(useCase)
                .codEligibility();

        responseBody.assertCodEligibilityResponse();
    }
}