package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.updateProfile.UpdateProfileClient;
import clients.users.post.updateProfile.UpdateProfileRequest;
import clients.users.post.updateProfile.UpdateProfileRequestBuilder;
import clients.users.post.updateProfile.UpdateProfileResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class UpdateProfileTest {

    //Update phone is blocked after TD integration
    @Test(groups = Categories.TODO)
    public void ShouldBeAbleToUpdateProfile() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        UpdateProfileRequest request = new UpdateProfileRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withEmail(useCase.getUser().getUsername())
                .withFirstName("Test")
                .withLastName("User")
                .withMobileNumber("8080808080")
                .withGender("Male")
                .withDateOfBirth("15/02/1997")
                .build();

        UpdateProfileResponseBody responseBody = new UpdateProfileClient().updateProfile(request);

        responseBody.assertUpdateProfilerResponse(useCase);

    }
}