package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.mobileNumber.MobileNumberClient;
import clients.mobileNumberLogin.post.mobileNumber.MobileNumberRequest;
import clients.mobileNumberLogin.post.mobileNumber.MobileNumberRequestBuilder;
import clients.mobileNumberLogin.post.mobileNumber.MobileNumberResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

@Test(groups = Categories.UNIT)
public class MobileNumberTest {

    public void ShouldBeableToValidateMobileNumber() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerRegistrationStep().registerUser();

        model model = new model(useCase.getUser().getUsername(),"","",useCase.getUser().getPassword(),"");
        MobileNumberRequest request = new MobileNumberRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        MobileNumberResponseBody responseBody = new MobileNumberClient().validateMobileNumber(request);

        responseBody.assertMobileNumberResponse();
    }
}
