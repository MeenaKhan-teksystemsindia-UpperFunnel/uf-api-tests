package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.get.address.getAddresses.response.GetAddressesResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetAddressesTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetAddresses() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        GetAddressesResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetAddressStep()
                .getAddress();

        responseBody.assertGetAddressesResponse(useCase);
    }
}
