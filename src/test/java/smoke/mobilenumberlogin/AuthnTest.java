package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.authn.AuthnClient;
import clients.mobileNumberLogin.post.authn.AuthnRequest;
import clients.mobileNumberLogin.post.authn.AuthnRequestBuilder;
import clients.mobileNumberLogin.post.authn.AuthnResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.SMOKE)
public class AuthnTest {

    public void ShouldBeableToGenerateAuthToken() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","","password","");
        AuthnRequest request = new AuthnRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        AuthnResponseBody responseBody = new AuthnClient().getAuthnToken(request);

        responseBody.assertAuthnResponse();
    }
}
