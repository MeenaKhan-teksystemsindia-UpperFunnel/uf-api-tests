package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.binValidation.response.BinValidationResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.EmiCreditCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class BinValidationForStandardEmiTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoBinValidationForStandardEmi() throws Exception {

        Card axisBankEmiCreditCard = new EmiCreditCards().getAxisBankEmiCreditCard();
        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(6).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.STANDARDEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .withCard(axisBankEmiCreditCard)
                .build();

        BinValidationResponseBody responseBody = new StartingStep(useCase)
                .proceedTillBinValidationStep()
                .binValidation();

        responseBody.assertBinValidationResponseForEmi();
    }
}
