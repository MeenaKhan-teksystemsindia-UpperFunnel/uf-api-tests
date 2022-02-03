package smoke.juspay;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.juspay.post.jusPayTransaction.response.JusPayTransactionResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class JusPayTransactionDebitCardTest {

    @Test(groups = Categories.SMOKE)
    public void shouldMakeJusPayTransaction() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .withCard(debitCard)
                .build();

        JusPayTransactionResponseBody responseBody = new StartingStep(useCase)
                .proceedTillJusPayTransactionStep()
                .jusPayTransaction();

        responseBody.assertJusPayTransactionRespons(useCase);
    }
}
