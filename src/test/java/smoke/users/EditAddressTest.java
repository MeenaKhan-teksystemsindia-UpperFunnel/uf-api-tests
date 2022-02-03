package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.get.address.getAddresses.response.GetAddressesResponseBody;
import clients.users.post.address.editAddress.EditAddressClient;
import clients.users.post.address.editAddress.EditAddressRequest;
import clients.users.post.address.editAddress.EditAddressRequestBuilder;
import clients.users.post.address.editAddress.EditAddressResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.GetAddressStep;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;


public class EditAddressTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToEditAddress() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        GetAddressesResponseBody getAddressesResponseBody = new StartingStep(useCase)
                .proceedTillGetAddressStep()
                .getAddress();
        getAddressesResponseBody.assertGetAddressesResponse(useCase);

        String newPinCode = ProductDataService.getInstance().getUnserviceableProduct(1).getPinCode();
        useCase.getDeliveryAddress().setPostalCode(newPinCode);

        EditAddressRequest editAddressRequest = new EditAddressRequestBuilder()
                .withUsername(useCase.getUser().getUsername())
                .withBearerToken(UseCase.bearerToken)
                .withEmailId(useCase.getUser().getUsername())
                .withAddress(useCase.getDeliveryAddress())
                .build();

        EditAddressResponseBody editAddressResponseBody = new EditAddressClient()
                .editAddress(editAddressRequest);
        editAddressResponseBody.assertEditAddressResponse();

        GetAddressesResponseBody getNewAddressesResponseBody = new GetAddressStep(useCase).getAddress();
        Assert.assertNotEquals(getAddressesResponseBody.getAddresses().get(0).getPostalCode(),
                getNewAddressesResponseBody.getAddresses().get(0).getPostalCode());
    }
}