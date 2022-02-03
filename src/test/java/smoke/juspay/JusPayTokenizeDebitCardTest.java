package smoke.juspay;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.juspay.post.jusPayTokenize.JusPayTokenizeResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class JusPayTokenizeDebitCardTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGetJusPayToken() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .withCard(debitCard)
                .build();

        JusPayTokenizeResponseBody responseBody = new StartingStep(useCase)
                .proceedTillJusPayTokenizeStep()
                .getJusPayToken();

        responseBody.assertJusPayTokenizeResponse();
    }
}
