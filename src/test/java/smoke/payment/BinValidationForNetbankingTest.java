package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.binValidation.response.BinValidationResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class BinValidationForNetbankingTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoBinValidationForNetbanking() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NETBANKING))
                .withBank(Bank.HDFC)
                .build();

        BinValidationResponseBody responseBody = new StartingStep(useCase)
                .proceedTillBinValidationStep()
                .binValidation();

        responseBody.assertBinValidationResponseForNetbanking();
    }
}
