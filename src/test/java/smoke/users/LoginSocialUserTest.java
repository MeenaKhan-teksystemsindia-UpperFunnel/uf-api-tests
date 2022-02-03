package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.loginSocialUser.LoginSocialUserClient;
import clients.users.post.loginSocialUser.LoginSocialUserRequest;
import clients.users.post.loginSocialUser.LoginSocialUserRequestBuilder;
import clients.users.post.loginSocialUser.response.LoginSocialUserResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class LoginSocialUserTest {

    //this API is deprecated
    @Test(groups = Categories.TODO)
    public void ShouldBeAbleToLoginSocialUser() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        LoginSocialUserRequest request = new LoginSocialUserRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .withEmail(useCase.getUser().getUsername())
                .build();

        LoginSocialUserResponseBody responseBody = new LoginSocialUserClient().loginSocialUser(request);

        responseBody.assertLoginSocialUserResponse(useCase.getUser().getUsername());

    }
}
