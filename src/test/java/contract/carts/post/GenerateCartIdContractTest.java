package contract.carts.post;

import builder.UseCaseBuilder;
import clients.carts.post.generateCartId.GenerateCartIdClient;
import clients.carts.post.generateCartId.GenerateCartIdRequest;
import clients.carts.post.generateCartId.GenerateCartIdRequestBuilder;
import entities.UseCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorsResponse;
import steps.StartingStep;
import util.Categories;

public class GenerateCartIdContractTest {

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
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase)
                .proceedTillGetUserBearerAccessTokenStep()
                .userBearerAccessToken();

        GenerateCartIdRequest request = new GenerateCartIdRequestBuilder()
                .withUsername(useCase.getUser().getUsername())
                .withBearerToken(bearerToken)
                .build();

        ErrorsResponse response = new GenerateCartIdClient().generateCartIdExpectingError(request);
        response.assertErrorsResponse(type, message, statusCode);
    }
}
