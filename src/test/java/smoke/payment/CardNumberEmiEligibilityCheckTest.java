package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.cardNumberEmiEligibility.response.CardNumberEmiEligibilityResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.EmiCreditCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class CardNumberEmiEligibilityCheckTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoCardNumberEmiEligibilityCheck() throws Exception {

        Card axisBankEmiCreditCard = new EmiCreditCards().getAxisBankEmiCreditCard();
        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(9).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NOCOSTEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .withCard(axisBankEmiCreditCard)
                .build();

        CardNumberEmiEligibilityResponseBody responseBody = new StartingStep(useCase)
                .proceedTillCardNumberEmiEligibilityCheckStep()
                .cardNumberEmiEligibilityCheck();

        responseBody.assertEmiEligibleBinResponse();
    }
}
