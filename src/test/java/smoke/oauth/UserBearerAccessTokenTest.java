package smoke.oauth;

import builder.UseCaseBuilder;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class UserBearerAccessTokenTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetUserBearerAccessToken() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        UserBearerAccessTokenResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetUserBearerAccessTokenStep()
                .userBearerAccessToken();

        responseBody.assertUserBearerAccessTokenResponse();
    }
}
