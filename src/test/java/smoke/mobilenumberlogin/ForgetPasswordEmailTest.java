package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.forgetPasswordEmail.ForgetPasswordEmailClient;
import clients.mobileNumberLogin.post.forgetPasswordEmail.ForgetPasswordEmailRequest;
import clients.mobileNumberLogin.post.forgetPasswordEmail.ForgetPasswordEmailRequestBuilder;
import clients.mobileNumberLogin.post.forgetPasswordEmail.ForgetPasswordEmailResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.SMOKE)
public class ForgetPasswordEmailTest {

    public void ShouldBeAbleToSendOTP() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),
                "","", useCase.getUser().getPassword(),"");

        ForgetPasswordEmailRequest request = new ForgetPasswordEmailRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        ForgetPasswordEmailResponseBody responseBody = new ForgetPasswordEmailClient().ForgetPasswordEmail(request);

        responseBody.assertForgetPasswordEmailResponse();
    }
}
