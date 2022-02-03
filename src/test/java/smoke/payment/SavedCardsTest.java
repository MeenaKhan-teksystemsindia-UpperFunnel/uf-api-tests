package smoke.payment;

import builder.UseCaseBuilder;
import clients.payment.post.savedCards.SavedCardsClient;
import clients.payment.post.savedCards.SavedCardsRequest;
import clients.payment.post.savedCards.SavedCardsRequestBuilder;
import clients.payment.post.savedCards.response.SavedCardsResponseBody;
import entities.UseCase;
import entities.User;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.userData.UserDataService;
import util.Categories;

public class SavedCardsTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetSavedCards() throws Exception {

        User user = UserDataService.getInstance().getKYCVerifiedUser();
        UseCase useCase = new UseCaseBuilder()
                .withUser(user)
                .build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        SavedCardsRequest request = new SavedCardsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        SavedCardsResponseBody responseBody = new SavedCardsClient().getSavedCards(request);
        responseBody.assertSavedCardsResponse();
    }
}