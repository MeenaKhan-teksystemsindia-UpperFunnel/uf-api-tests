package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.removeSavedCards.RemoveSavedCardsClient;
import clients.payment.post.removeSavedCards.RemoveSavedCardsRequest;
import clients.payment.post.removeSavedCards.RemoveSavedCardsRequestBuilder;
import clients.payment.post.removeSavedCards.RemoveSavedCardsResponseBody;
import clients.payment.post.savedCards.SavedCardsClient;
import clients.payment.post.savedCards.SavedCardsRequest;
import clients.payment.post.savedCards.SavedCardsRequestBuilder;
import clients.payment.post.savedCards.response.SavedCardsResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.CreditCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class RemoveSavedCardsTest {

    @Test(groups = Categories.SMOKE, enabled = false)
    public void shouldBeAbleToRemoveSavedCards() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();
        Card creditCard = new CreditCards().getCreditCard();

        UseCase useCase = new UseCaseBuilder()
                .withDeliveryAddress(address)
                .withProducts(Arrays.asList(product))
                .withPaymentModes(Arrays.asList(PaymentMode.CREDITCARD))
                .withSaveCardToMyAccount(true)
                .withCard(creditCard)
                .build();

        new StartingStep(useCase).proceedTillGetSelectedOrderV1Step().getSelectedOrderV1();

        SavedCardsRequest request = new SavedCardsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        SavedCardsResponseBody savedCardsResponseBody = new SavedCardsClient().getSavedCards(request);

        savedCardsResponseBody.assertSavedCardsResponse();

        RemoveSavedCardsRequest removeSavedCardsRequest = new RemoveSavedCardsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCardToken(savedCardsResponseBody.getSavedCardDetailsMap().get(0).getValue().getCardToken())
                .build();

        RemoveSavedCardsResponseBody removeSavedCardsResponseBody = new RemoveSavedCardsClient().removeSavedCards(removeSavedCardsRequest);

        removeSavedCardsResponseBody.assertRemoveSavedCardsResponse();

    }
}