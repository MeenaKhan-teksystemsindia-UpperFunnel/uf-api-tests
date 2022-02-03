package contract.carts.get;

import builder.UseCaseBuilder;
import clients.carts.get.getCarts.GetCartsClient;
import clients.carts.get.getCarts.GetCartsRequest;
import clients.carts.get.getCarts.GetCartsRequestBuilder;
import entities.UseCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorsResponse;
import steps.StartingStep;
import util.Categories;

public class GetCartsContractTest {

    @DataProvider(name = "InvalidAccessToken")
    private static Object[][] getInvalidAccessToken() {

        return new Object[][]{

                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401},
                {"bceffccf-7f99-4e28-8816-943e9d53b0zz", "InvalidTokenError",
                        "Invalid access token: bceffccf-7f99-4e28-8816-943e9d53b0zz", 401}
        };
    }

    @Test(dataProvider = "InvalidAccessToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase)
                .proceedTillCustomerLoginStep()
                .customerLogin();

        GetCartsRequest request = new GetCartsRequestBuilder()
                .withBearerToken(bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorsResponse response = new GetCartsClient().getCartsExpectingError(request);
        response.assertErrorsResponse(type, message, statusCode);
    }
}