package contract.carts.post;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.deliverymode.SelectDeliveryModeForOrderClient;
import clients.carts.post.deliverymode.SelectDeliveryModeForOrderRequest;
import clients.carts.post.deliverymode.SelectDeliveryModeForOrderRequestBuilder;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import responses.errors.ErrorsResponse;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class SelectDeliveryModeContractTest {
    @DataProvider(name = "InvalidAccessToken")
    private static Object[][] getInvalidAccessToken() {
        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401}
        };
    }

    @Test(dataProvider = "InvalidAccessToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) throws Exception {
        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .build();

        new StartingStep(useCase).proceedTillSelectDeliveryModeStep();

        SelectDeliveryModeForOrderRequest request = new SelectDeliveryModeForOrderRequestBuilder()
                .withBearerToken(bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();
        ErrorsResponse response = new SelectDeliveryModeForOrderClient().selectDeliveryModeExpectingErrors(request);
        response.assertErrorsResponse(type, message, statusCode);

    }
}