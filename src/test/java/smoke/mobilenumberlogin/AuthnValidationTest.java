package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.authnValidation.AuthnValidationClient;
import clients.mobileNumberLogin.post.authnValidation.AuthnValidationRequest;
import clients.mobileNumberLogin.post.authnValidation.AuthnValidationRequestBuilder;
import clients.mobileNumberLogin.post.authnValidation.AuthnValidationResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.SMOKE)
public class AuthnValidationTest {

    public void ShouldBeableToDoAuthnValidation() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","","password","");
        AuthnValidationRequest request = new AuthnValidationRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        AuthnValidationResponseBody responseBody = new AuthnValidationClient().getAuthnValidation(request);

        responseBody.assertAuthnValidationResponse();
    }
}
