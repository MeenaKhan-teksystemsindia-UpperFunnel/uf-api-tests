package smoke.users;

import builder.UseCaseBuilder;
import clients.users.get.getCustomerProfile.GetCustomerProfileClient;
import clients.users.get.getCustomerProfile.GetCustomerProfileRequest;
import clients.users.get.getCustomerProfile.GetCustomerProfileRequestBuilder;
import clients.users.get.getCustomerProfile.Response.GetCustomerProfileResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class GetCustomerProfileTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetAllWishlist() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        GetCustomerProfileRequest request = new GetCustomerProfileRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .build();

        GetCustomerProfileResponseBody responseBody = new GetCustomerProfileClient().getCustomerProfile(request);

        responseBody.assertGetCustomerProfileResponse(useCase.getUser().getUsername());

    }
}
