package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.binValidation.response.BinValidationResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class BinValidationTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoBinValidation() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        BinValidationResponseBody responseBody = new StartingStep(useCase)
                .proceedTillBinValidationStep()
                .binValidation();

        responseBody.assertBinValidationResponse();
    }
}
