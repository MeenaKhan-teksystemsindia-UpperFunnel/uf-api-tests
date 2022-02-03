package contract.users.get;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.get.address.getAddresses.GetAddressesClient;
import clients.users.get.address.getAddresses.GetAddressesRequest;
import clients.users.get.address.getAddresses.GetAddressesRequestBuilder;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorsResponse;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetAddressesContractTest {

    @DataProvider(name = "InvalidBearerToken")
    private static Object[][] getInvalidBearerToken() {
        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"test", "InvalidTokenError", "Invalid access token: test", 401}
        };
    }

    @Test(dataProvider = "InvalidBearerToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase).proceedTillGetAddressStep();

        GetAddressesRequest request = new GetAddressesRequestBuilder()
                .withBearerToken(bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorsResponse response = new GetAddressesClient().getAddressExpectingError(request);

        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "InvalidUsername")
    private static Object[][] getInvalidUsername() {
        return new Object[][]{
                // username, errorType, errorMessage, statusCode
                {"test", "ForbiddenError", "Access is denied", 403}
        };

    }

    @Test(dataProvider = "InvalidUsername", groups = Categories.CONTRACT)
    public void shouldThrowInvalidUserNameError(String userName, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase).proceedTillGetAddressStep();

        GetAddressesRequest request = new GetAddressesRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(userName)
                .build();

        ErrorsResponse response = new GetAddressesClient().getAddressExpectingError(request);

        response.assertErrorsResponse(type, message, statusCode);
    }
}