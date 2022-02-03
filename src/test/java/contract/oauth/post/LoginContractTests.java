package contract.oauth.post;

import clients.oauth.post.userAccessToken.UserBearerAccessTokenClient;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenRequest;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenRequestBuilder;
import entities.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorsResponse;
import util.Categories;

public class LoginContractTests {

    @DataProvider(name = "BadRequestData")
    private static Object[][] getBadRequestData() {
        return new Object[][]{

                // username, password, errorType, errorMessage
                {"", "", "InvalidGrantError", "Username is empty, please use Email ID to log in"},
                {"", "12345678", "InvalidGrantError", "Username is empty, please use Email ID to log in"},
                {"InvalidEmailId", "12345678", "InvalidGrantError", "Email ID or phone number does not exist"},
                {"NonExisting@somemail.com", "12345678", "InvalidGrantError", "Email ID or phone number does not exist"},
                {"automation@gmail.com", "", "InvalidGrantError", "The user name or password entered is incorrect. Please try again"},
                {"automation@gmail.com", "IncorrectPwd", "InvalidGrantError", "The user name or password entered is incorrect. Please try again"},
        };
    }

    @Test(dataProvider = "BadRequestData", groups = Categories.CONTRACT)
    public void shouldThrowBadRequest(String username, String password, String type, String message) {

        User user = new User(username, password);
        UserBearerAccessTokenRequest request = new UserBearerAccessTokenRequestBuilder().withUser(user).build();

        ErrorsResponse response = new UserBearerAccessTokenClient().userBearerAccessTokenExpectingError(request);
        response.assertErrorsResponse(type, message, 400);
    }

    @DataProvider(name = "InvalidGrantType")
    private static Object[][] getInvalidGrantType() {
        return new Object[][]{

                // grantType, errorType, errorMessage, statusCode
                {"", "InvalidRequestError", "Missing grant type",400},
                {"invalid", "UnsupportedGrantTypeError", "Unsupported grant type: invalid",400},
        };
    }

    @Test(dataProvider = "InvalidGrantType", groups = Categories.CONTRACT)
    public void shouldThrowBadRequestForInvalidGrantType(String grantType, String type, String message,int statusCode) {

        UserBearerAccessTokenRequest request = new UserBearerAccessTokenRequestBuilder().withGrantType(grantType).build();

        ErrorsResponse response = new UserBearerAccessTokenClient().userBearerAccessTokenExpectingError(request);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "InvalidClientSecret")
    private static Object[][] getInvalidClientSecret() {
        return new Object[][]{

               // clientID, errorType, errorMessage, statusCode
                {"", "BadClientCredentialsError", "Bad client credentials",401},
                {"invalid", "BadClientCredentialsError", "Bad client credentials",401},
        };
    }

    @Test(dataProvider = "InvalidClientSecret", groups = Categories.CONTRACT)
    public void shouldThrowBadRequestForInvalidClientSecret(String clientSecret, String type, String message, int statusCode) {

        UserBearerAccessTokenRequest request = new UserBearerAccessTokenRequestBuilder().withClientSecret(clientSecret)
                .build();

        ErrorsResponse response = new UserBearerAccessTokenClient().userBearerAccessTokenExpectingError(request);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "UnauthorizedClientId")
    private static Object[][] getInvalidClientId() {
        return new Object[][]{

               // clientSecret, errorType, errorMessage, statusCode
                {"", "BadClientCredentialsError", "Bad client credentials",401},
                {"invalid", "BadClientCredentialsError", "Bad client credentials",401},
        };
    }

    @Test(dataProvider = "UnauthorizedClientId", groups = Categories.CONTRACT)
    public void shouldThrowBadRequestForInvalidClientId(String clientId, String type, String message, int statusCode) {

        UserBearerAccessTokenRequest request = new UserBearerAccessTokenRequestBuilder().withClientId(clientId)
                .build();

        ErrorsResponse response = new UserBearerAccessTokenClient().userBearerAccessTokenExpectingError(request);
        response.assertErrorsResponse(type, message, statusCode);
    }
}
