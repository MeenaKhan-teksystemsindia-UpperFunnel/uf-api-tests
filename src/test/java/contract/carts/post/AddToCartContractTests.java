package contract.carts.post;

import builder.UseCaseBuilder;
import clients.carts.post.addToCart.AddToCartClient;
import clients.carts.post.addToCart.AddToCartRequest;
import clients.carts.post.addToCart.AddToCartRequestBuilder;
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

public class AddToCartContractTests {


    @DataProvider(name = "InvalidBearerToken")
    private static Object[][] getInvalidBearerToken() {
        return new Object[][]{
                // access-token, errorType, errorMessage, statusCode
                {"", "InvalidTokenError", "Invalid access token: ", 401},
                {"abc", "InvalidTokenError", "Invalid access token: abc", 401},
                {"bceffccf-7f99-4e28-8816-943e9d53b0zz", "InvalidTokenError", "Invalid access token: bceffccf-7f99-4e28-8816-943e9d53b0zz", 401}
        };
    }

    @Test(dataProvider = "InvalidBearerToken", groups = Categories.CONTRACT)
    public void shouldThrowInvalidAccessTokenError(String bearerToken, String type, String message, int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase).proceedTillAddToCartStep();

        AddToCartRequest request = new AddToCartRequestBuilder()
                .withProduct(product)
                .withBearerToken(bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .build();

        ErrorsResponse response = new AddToCartClient().addToCartExpectingErrorList(request);
        response.assertErrorsResponse(type, message, statusCode);
    }

    @DataProvider(name = "InvalidQuantity")
    private static Object[][] getInvalidQuantity() {
        return new Object[][]{
                // quantity, errorType, errorMessage, status,statusCode
                {0, "webSerResponseWsDTO", "Invalid product quantity :", "Failure",400},
                {-1, "webSerResponseWsDTO", "Invalid product quantity :", "Failure",400},
        };
    }

    @Test(dataProvider = "InvalidQuantity", groups = Categories.CONTRACT)
    public void shouldThrowInvalidQuantityError(int quantity, String type, String errorMessage, String status,int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(quantity);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase).proceedTillAddToCartStep();

        AddToCartRequest request = new AddToCartRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .build();

        ErrorResponse response = new AddToCartClient().addToCartExpectingError(request);
        response.assertErrorResponse(type, errorMessage, status, statusCode);
    }

    @DataProvider(name = "InvalidProductCode")
    private static Object[][] getInvalidProductCode() {
        return new Object[][]{
                // productCode, errorType, status, statusCode
                {"", "webSerResponseWsDTO", "Failure",400},
                {"123", "webSerResponseWsDTO", "Failure",400},
                {"abc", "webSerResponseWsDTO", "Failure",400},
                {"@#$", "webSerResponseWsDTO", "Failure",400}
        };
    }

    @Test(dataProvider = "InvalidProductCode")
    public void shouldThrowInvalidProductCodeError(String productCode, String type, String status,int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        product.setProductCode(productCode);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase).proceedTillAddToCartStep();

        AddToCartRequest request = new AddToCartRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .build();

        ErrorResponse response = new AddToCartClient().addToCartExpectingError(request);
        response.assertErrorResponse(type, null, status, statusCode);
    }

    @DataProvider(name = "InvalidUSSID")
    private static Object[][] getInvalidUSSID() {
        return new Object[][]{
                // productCode, errorType, status , statusCode
                {"", "webSerResponseWsDTO", "Failure",400},
                {"123", "webSerResponseWsDTO", "Failure",400},
                {"abc", "webSerResponseWsDTO", "Failure",400},
                {"@#$", "webSerResponseWsDTO", "Failure",400}
        };
    }

    @Test(dataProvider = "InvalidUSSID")
    public void shouldThrowInvalidInvalidUSSIDError(String productCode, String type, String status,int statusCode) throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        product.setProductCode(productCode);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase).proceedTillAddToCartStep();

        AddToCartRequest request = new AddToCartRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .build();

        ErrorResponse response = new AddToCartClient().addToCartExpectingError(request);
        response.assertErrorResponse(type, null, status, statusCode);
    }
}
