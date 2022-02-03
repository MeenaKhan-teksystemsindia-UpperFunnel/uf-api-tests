package smoke.cart;

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
import testbase.TestBase;
import util.Categories;

import java.util.Arrays;

public class CheckPincodeTest extends TestBase {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCheckPincodeForOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(product)).build();

        new StartingStep(useCase)
                .proceedTillCustomerLoginStep()
                .customerLogin();

        CheckPincodeRequest request = new CheckPincodeRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        CheckPincodeResponseBody responseBody
                = new CheckPincodeClient().checkPincodeForProductCode(request);

        responseBody.assertCheckPincodeResponse(useCase);
    }
}
