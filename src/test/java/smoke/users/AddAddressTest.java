package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.post.address.addAddress.AddAddressClient;
import clients.users.post.address.addAddress.AddAddressRequest;
import clients.users.post.address.addAddress.AddAddressRequestBuilder;
import clients.users.post.address.addAddress.response.AddAddressResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import testbase.TestBase;
import util.Categories;

import java.util.Arrays;

public class AddAddressTest extends TestBase {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToAddAddress() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase)
                .proceedTillCustomerLoginStep()
                .customerLogin();

        AddAddressRequest request = new AddAddressRequestBuilder()
                .withUsername(useCase.getUser().getUsername())
                .withBearerToken(UseCase.bearerToken)
                .withAddress(address)
                .build();

        AddAddressResponseBody responseBody = new AddAddressClient().addAddress(request);

        responseBody.assertAddAddressResponse();
    }
}
