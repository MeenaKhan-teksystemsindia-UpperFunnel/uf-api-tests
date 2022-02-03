package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.allStores.GetAllStoresClient;
import clients.carts.get.allStores.GetAllStoresRequest;
import clients.carts.get.allStores.GetAllStoresRequestBuilder;
import clients.carts.get.allStores.response.GetAllStoresResponseBody;
import clients.carts.post.addStore.AddStoreClient;
import clients.carts.post.addStore.AddStoreRequest;
import clients.carts.post.addStore.AddStoreRequestBuilder;
import clients.carts.post.addStore.response.AddStoreResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class AddStoreTest {

    //Changed to TODO as store list is growing big, not option to remove
    @Test(groups = Categories.TODO)
    public void shouldAddStore() throws Exception {

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

        GetAllStoresResponseBody responseBody=new GetAllStoresClient().getAllStores(request);

        AddStoreRequest addStoreRequest = new AddStoreRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUssid(product.getUssid())
                .withUsername(useCase.getUser().getUsername())
                .withSlaveId(responseBody.getStores().get(0).getSlaveId())
                .withCartCode(useCase.getCartCode())
                .build();

        AddStoreResponseBody addStoreResponseBody = new AddStoreClient().addStore(addStoreRequest);
        addStoreResponseBody.assertAddStoreResponse();
    }
}
