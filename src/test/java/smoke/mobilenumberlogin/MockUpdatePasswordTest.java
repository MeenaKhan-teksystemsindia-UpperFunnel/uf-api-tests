package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.updatePassword.UpdatePasswordClient;
import clients.mobileNumberLogin.post.updatePassword.UpdatePasswordRequest;
import clients.mobileNumberLogin.post.updatePassword.UpdatePasswordRequestBuilder;
import clients.mobileNumberLogin.post.updatePassword.UpdatePasswordResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.UNIT)
public class MockUpdatePasswordTest {

    public void ShouldBeAbleToUpdatePassword() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","123456","password1","");

        UpdatePasswordRequest request = new UpdatePasswordRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        UpdatePasswordResponseBody responseBody = new UpdatePasswordClient().updatePassword(request);

        responseBody.assertUpdatePasswordResponse();
    }
}
