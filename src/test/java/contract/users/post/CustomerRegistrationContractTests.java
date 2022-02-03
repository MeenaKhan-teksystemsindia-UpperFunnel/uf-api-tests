package contract.users.post;

import clients.oauth.post.globalAccessToken.GlobalAccessTokenClient;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequest;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequestBuilder;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenClient;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenRequest;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenRequestBuilder;
import clients.oauth.post.userAccessToken.UserBearerAccessTokenResponseBody;
import clients.users.post.registration.customerRegistration.CustomerRegistrationClient;
import clients.users.post.registration.customerRegistration.CustomerRegistrationRequest;
import clients.users.post.registration.customerRegistration.CustomerRegistrationRequestBuilder;
import entities.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorResponse;
import responses.errors.ErrorsResponse;
import util.Categories;

public class CustomerRegistrationContractTests {
    private String username;
    private String password;
    private String globalAccessToken;

    @BeforeClass
    public void registerNewUser() {

        GlobalAccessTokenRequest globalAccessTokenRequest = new GlobalAccessTokenRequestBuilder().build();
        globalAccessToken = new GlobalAccessTokenClient().globalAccessToken(globalAccessTokenRequest).getAccessToken();

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken).build();
        new CustomerRegistrationClient().customerRegistration(customerRegistrationRequest);

        username = customerRegistrationRequest.getEmailId();
        password = customerRegistrationRequest.getPassword();
    }

    @DataProvider(name = "InvalidAccessToken")
    private static Object[][] getInvalidAccessToken() {
        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"testToken", "InvalidTokenError", "Invalid access token: testToken", 401}
        };
    }

    @Test(dataProvider = "InvalidAccessToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String globalAccessToken, String type, String message, int statusCode) {

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken).build();

        ErrorsResponse response = new CustomerRegistrationClient()
                .customerRegistrationExpectingErrorList(customerRegistrationRequest);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @Test(groups = Categories.CONTRACT)
    public void shouldThrowAccessDeniedErrorWhenRegisteringNewUserWithBearerToken() {

        User user = new User(username, password);
        UserBearerAccessTokenRequest userBearerAccessTokenRequest
                = new UserBearerAccessTokenRequestBuilder().withUser(user).build();
        UserBearerAccessTokenResponseBody userBearerAccessTokenResponse = new UserBearerAccessTokenClient().userBearerAccessToken(userBearerAccessTokenRequest);

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(userBearerAccessTokenResponse.getAccessToken())
                        .build();

        ErrorsResponse response = new CustomerRegistrationClient()
                .customerRegistrationExpectingErrorList(customerRegistrationRequest);
        response.assertErrorsResponse("AccessDeniedError", "Access is denied", 401);
    }

    @DataProvider(name = "InvalidEmail")
    private static Object[][] getInvalidEmail() {
        return new Object[][]{
                // email, errorType, errorMessage, status
                {"", "mplUserResultWsDto", "Please enter a valid email ID.", "Failure", 400},
                {"test", "mplUserResultWsDto", "Please enter a valid email with maximum 240 characters.", "Failure", 400}
        };
    }

    @Test(dataProvider = "InvalidEmail", groups = Categories.CONTRACT)
    public void shouldThrowInvalidEmailError(String email, String type, String errorMessage, String status, int statusCode) {

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken)
                        .build();
        customerRegistrationRequest.setEmailId(email);

        ErrorResponse response = new CustomerRegistrationClient()
                .customerRegistrationExpectingError(customerRegistrationRequest);
        response.assertErrorResponse(type, errorMessage, status, statusCode);
    }

    @DataProvider(name = "InvalidPassword")
    private static Object[][] getInvalidPassword() {
        return new Object[][]{
                //password, errorType, errorMessage, status
                {"", "mplUserResultWsDto", "Please enter a strong password (at least 8 chars).", "Failure", 400},
                {"test", "mplUserResultWsDto", "Please enter a strong password (at least 8 chars).", "Failure", 400}
        };
    }

    @Test(dataProvider = "InvalidPassword", groups = Categories.CONTRACT)
    public void shouldThrowInvalidPasswordError(String password, String type, String errorMessage, String status, int statusCode) {

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken)
                        .build();
        customerRegistrationRequest.setPassword(password);

        ErrorResponse response = new CustomerRegistrationClient()
                .customerRegistrationExpectingError(customerRegistrationRequest);
        response.assertErrorResponse(type, errorMessage, status, statusCode);
    }

    @Test(groups = Categories.CONTRACT)
    public void shouldNotRegisterWithExistingUserCredentials() {

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken)
                        .build();
        customerRegistrationRequest.setEmailId(username);
        customerRegistrationRequest.setPassword(password);

        ErrorResponse response = new CustomerRegistrationClient()
                .customerRegistrationExpectingError(customerRegistrationRequest);

        String errorMessage = "Email Id already exists, please try with another email Id!";
        response.assertErrorResponse("mplUserResultWsDto", errorMessage, "Failure", 400);
    }
}
