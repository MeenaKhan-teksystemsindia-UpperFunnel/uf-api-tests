package businessRules;

import builder.UseCaseBuilder;
import clients.carts.post.pincodeForProduct.CheckPincodeClient;
import clients.carts.post.pincodeForProduct.CheckPincodeRequest;
import clients.carts.post.pincodeForProduct.CheckPincodeRequestBuilder;
import clients.carts.post.pincodeForProduct.response.CheckPincodeResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class CheckPinCodeValidationTest {

    @Test(groups = Categories.BUSINESS_VALIDATION)
    public void shouldThrowErrorWhenProductIsUnserviceableForPinCode() throws Exception {

        Product product = ProductDataService.getInstance().getUnserviceableProduct( 1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        new StartingStep(useCase)
                .proceedTillCustomerLoginStep()
                .customerLogin();

        CheckPincodeRequest checkPincodeRequest = new CheckPincodeRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        CheckPincodeResponseBody checkPincodeResponse
                = new CheckPincodeClient().checkPincodeForProductCode(checkPincodeRequest);

        String productNotServiceabilityMessage = "The product is currently unavailable for your pincode, please check again later";

        assertEquals(checkPincodeResponse.getProductNotServiceabilityMessage(), productNotServiceabilityMessage);
    }
}
