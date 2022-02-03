package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.validate.ValidateClient;
import clients.mobileNumberLogin.post.validate.ValidateRequest;
import clients.mobileNumberLogin.post.validate.ValidateRequestBuilder;
import clients.mobileNumberLogin.post.validate.response.ValidateResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.SMOKE)
public class ValidateTest {

    public void ShouldBeableToValidate() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","","password","");
        ValidateRequest request = new ValidateRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        ValidateResponseBody responseBody = new ValidateClient().getValidate(request);

        responseBody.assertValidateResponse();
    }
}
