package contract.pincode.get;

import clients.oauth.post.globalAccessToken.GlobalAccessTokenClient;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequest;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequestBuilder;
import clients.pincode.get.pincodeData.PincodeDataClient;
import clients.pincode.get.pincodeData.PincodeDataRequest;
import clients.pincode.get.pincodeData.PincodeDataRequestBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorResponse;
import responses.errors.ErrorsResponse;
import util.Categories;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PincodeDataContractTests {

    public String globalAccessToken;

    @BeforeMethod
    private void getGlobalAccessToken() {

        GlobalAccessTokenRequest globalAccessTokenRequest = new GlobalAccessTokenRequestBuilder().build();
        globalAccessToken = new GlobalAccessTokenClient()
                .globalAccessToken(globalAccessTokenRequest)
                .getAccessToken();
    }

    @DataProvider(name = "InvalidAccessToken")
    private static Object[][] getInvalidAccessToken() {

        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401},
                {"bceffccf-7f99-4e28-8816-943e9d53b0zz", "InvalidTokenError", "Invalid access token: bceffccf-7f99-4e28-8816-943e9d53b0zz", 401}
        };
    }

    @Test(dataProvider = "InvalidAccessToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String globalAccessToken, String type, String message, int statusCode) {

        PincodeDataRequest request = new PincodeDataRequestBuilder()
                .withGlobalAccessToken(globalAccessToken)
                .withPincode("110001")
                .build();

        ErrorsResponse response = new PincodeDataClient().getPincodeDataExpectingErrorList(request);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "InvalidPincode")
    private static Object[][] getInvalidPincode() {

        return new Object[][]{
                // pincode, errorCode, status
                {"", "B9352", "FAILURE", 400},
                {"110a01", "B9352", "FAILURE", 400},
                {"11001", "B9352", "FAILURE", 400},
                {"aaaaaa", "B9352", "FAILURE", 400},
                {"aaaaa", "B9352", "FAILURE", 400}
        };
    }

    @Test(dataProvider = "InvalidPincode", groups = Categories.CONTRACT)
    public void shouldThrowBadRequestForInvalidPincode(String pincode, String errorCode, String status, int statusCode) {

        PincodeDataRequest request = new PincodeDataRequestBuilder()
                .withPincode(pincode)
                .withGlobalAccessToken(globalAccessToken)
                .build();

        ErrorResponse response = new PincodeDataClient().getPincodeDataExpectingError(request);
        assertEquals(response.getHttpStatusCode(), statusCode);

        assertTrue(errorCode.equals(response.getErrorCode()));
        assertTrue(status.equals(response.getStatus()));
    }
}
