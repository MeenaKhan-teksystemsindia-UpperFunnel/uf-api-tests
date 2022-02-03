package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.allStores.GetAllStoresClient;
import clients.carts.get.allStores.GetAllStoresRequest;
import clients.carts.get.allStores.GetAllStoresRequestBuilder;
import clients.carts.get.allStores.response.GetAllStoresResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetAllStoresTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGetAllStores() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase)
                .proceedTillAddAddressToOrderStep()
                .addAddressToOrder();

        GetAllStoresRequest request = new GetAllStoresRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withPinCode(product.getPinCode())
                .build();

        GetAllStoresResponseBody getAllStoresResponseBody = new GetAllStoresClient().getAllStores(request);
        getAllStoresResponseBody.assertGetAllStoresResponse();
    }
}
