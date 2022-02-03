package smoke.stripe;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.stripe.post.tokens.response.StripeTokensResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class StripeTokensDebitCardTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGetStripeTokenAndStripeCardToken() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .withCard(debitCard)
                .build();

        StripeTokensResponseBody responseBody = new StartingStep(useCase)
                .proceedTillStripeTokensStep()
                .getStripeTokens();

        responseBody.assertStripeTokensResponse();
    }
}
