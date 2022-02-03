package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.Registration.RegistrationClient;
import clients.mobileNumberLogin.post.Registration.RegistrationRequest;
import clients.mobileNumberLogin.post.Registration.RegistrationRequestBuilder;
import clients.mobileNumberLogin.post.Registration.RegistrationResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

import java.util.UUID;

@Test(groups = Categories.SMOKE)
public class RegistrationTest {

    public void ShouldBeAbleToRegister() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillGenerateGlobalAccessTokenStep().generateGlobalAccessToken();

        model model = new model(UUID.randomUUID().toString() + "@tctv.com",
                "7411376373","123456", "password","9731720222");

        RegistrationRequest request = new RegistrationRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        RegistrationResponseBody responseBody = new RegistrationClient().validateRegistration(request);

        responseBody.assertRegistrationResponse();
    }
}
