package contract.carts.post;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.addAddressToOrder.AddAddressToOrderClient;
import clients.carts.post.addAddressToOrder.AddAddressToOrderRequest;
import clients.carts.post.addAddressToOrder.AddAddressToOrderRequestBuilder;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorResponse;
import responses.errors.ErrorsResponse;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class AddAddressToOrderContractTest {

    @DataProvider(name = "InvalidBearerToken")
    private static Object[][] getInvalidBearerToken() {
        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401}
        };
    }

    @Test(dataProvider = "InvalidBearerToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address).build();

        new StartingStep(useCase).proceedTillAddAddressToOrderStep();

        AddAddressToOrderRequest request = new AddAddressToOrderRequestBuilder()
                .withBearerToken(bearerToken)
                .withAddressId(useCase.getDeliveryAddress().getAddressId())
                .withCartCode(useCase.getCartCode())
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorsResponse response = new AddAddressToOrderClient().getAddAddressToOrderExpectingErrorList(request);
        response.assertErrorsResponse(type, message, statusCode);

    }

    @DataProvider(name = "InvalidCartId")
    private static Object[][] getInvalidCartId() {
        return new Object[][]{
                // cartId, errorType, errorMessage, statusCode
                {"", "userResultWsDto", "CartModel is Empty", 400},
                {"test", "userResultWsDto", "CartModel is Empty", 400},
                {"1234", "userResultWsDto", "CartModel is Empty", 400}
        };

    }

    @Test(dataProvider = "InvalidCartId", groups = Categories.CONTRACT)
    public void shouldThrowInvalidCartIdError(String cartId, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address).build();

        new StartingStep(useCase).proceedTillAddAddressToOrderStep();

        AddAddressToOrderRequest request = new AddAddressToOrderRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withAddressId(useCase.getDeliveryAddress().getAddressId())
                .withCartCode(cartId)
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorResponse response = new AddAddressToOrderClient().getAddAddressToOrderExpectingError(request);
        response.assertErrorResponse(type, message, null, statusCode);
    }


    @DataProvider(name = "InvalidAddressId")
    private static Object[][] getInvalidAddressId() {
        return new Object[][]{
                // cartId, errorType, errorMessage, statusCode
                {"", "userResultWsDto", "MOBILEnot valid", 400}
        };

    }


    @Test(dataProvider = "InvalidAddressId", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAddressIdError(String addressId, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address).build();

        new StartingStep(useCase).proceedTillAddAddressToOrderStep();

        AddAddressToOrderRequest request = new AddAddressToOrderRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withAddressId(addressId)
                .withCartCode(useCase.getCartCode())
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorResponse response = new AddAddressToOrderClient().getAddAddressToOrderExpectingError(request);
        response.assertErrorResponse(type, message, null, statusCode);
    }
}


