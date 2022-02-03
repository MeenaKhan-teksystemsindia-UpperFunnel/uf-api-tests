package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.forgetPassword.ForgetPasswordClient;
import clients.users.post.forgetPassword.ForgetPasswordRequest;
import clients.users.post.forgetPassword.ForgetPasswordRequestBuilder;
import clients.users.post.forgetPassword.ForgetPasswordResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class ForgetPasswordTest {

    @Test(groups = Categories.SMOKE)
    public void ShouldBeAbleToSendForgetPasswordMail() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        ForgetPasswordRequest request = new ForgetPasswordRequestBuilder()
                .withBearerToken(useCase.getGlobalAccessToken())
                .withUserName(useCase.getUser().getUsername())
                .build();

        ForgetPasswordResponseBody responseBody = new ForgetPasswordClient().sendForgetPasswordMail(request);

        responseBody.assertForgetPasswordResponse(useCase.getUser().getUsername());

    }
}
