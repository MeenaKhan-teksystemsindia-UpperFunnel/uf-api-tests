package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.validation.ValidationClient;
import clients.mobileNumberLogin.post.validation.ValidationRequest;
import clients.mobileNumberLogin.post.validation.ValidationRequestBuilder;
import clients.mobileNumberLogin.post.validation.ValidationResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.SMOKE)
public class ValidationTest {

    public void ShouldBeableToValidation() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","","password","");
        ValidationRequest request = new ValidationRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        ValidationResponseBody responseBody = new  ValidationClient().getValidation(request);

        responseBody.assertValidationResponse();
    }
}
