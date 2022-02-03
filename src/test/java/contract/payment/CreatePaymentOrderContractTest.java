package contract.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.paymentOrder.createPaymentOrder.CreatePaymentOrderClient;
import clients.payment.post.paymentOrder.createPaymentOrder.CreatePaymentOrderRequest;
import clients.payment.post.paymentOrder.createPaymentOrder.CreatePaymentOrderRequestBuilder;
import entities.Address;
import entities.PaymentMode;
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

public class CreatePaymentOrderContractTest {

    @DataProvider(name = "InvalidBearerToken")
    private static Object[][] getInvalidAccessToken() {

        return new Object[][]{

                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401}

        };
    }

    @Test(dataProvider = "InvalidBearerToken", groups = Categories.CONTRACT)
    public void shouldThrowUnauthorizedError(String bearerToken, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase).proceedTillCreatePaymentOrderStep();

        CreatePaymentOrderRequest request = new CreatePaymentOrderRequestBuilder()
                .withBearerToken(bearerToken)
                .withCartId(useCase.getCartGuid())
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorsResponse response = new CreatePaymentOrderClient().createPaymentOrderExpectingErrorList(request);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "InvalidCartGuid")
    private static Object[][] getInvalidCartGuid() {

        return new Object[][]{

                // cartGuid, errorType, errorMessage, status,statusCode
                {"", "createPaymentOrderWsData","An exception occurred at back-end.","Failure",400},
                {"abc", "createPaymentOrderWsData","An exception occurred at back-end.","Failure",400}

        };
    }

    @Test(dataProvider = "InvalidCartGuid", groups = Categories.CONTRACT)
    public void shouldThrowInvalidCartGuidError(String cartGuid, String type,String errorMessage,String status,int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase).proceedTillCreatePaymentOrderStep();

        CreatePaymentOrderRequest request = new CreatePaymentOrderRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withCartId(cartGuid)
                .withUsername(useCase.getUser().getUsername())
                .build();

        ErrorResponse response = new CreatePaymentOrderClient().createPaymentOrderExpectingError(request);
        response.assertErrorResponse(type,errorMessage,status,statusCode);

    }
}
