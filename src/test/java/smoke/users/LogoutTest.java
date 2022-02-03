package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.logout.LogoutClient;
import clients.users.post.logout.LogoutRequest;
import clients.users.post.logout.LogoutRequestBuilder;
import clients.users.post.logout.LogoutResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class LogoutTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToLogout() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        LogoutRequest request = new LogoutRequestBuilder()
                .withUsername(useCase.getUser().getUsername())
                .withGlobalAccessToken(useCase.getGlobalAccessToken())
                .build();

        LogoutResponseBody responseBody = new LogoutClient().logout(request);

        responseBody.assertLogoutResponse();
    }
}
