package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.softReservationForCart.response.SoftReservationForCartResponseBody;
import clients.order.post.getEDD.GetEDDClient;
import clients.order.post.getEDD.GetEDDRequest;
import clients.order.post.getEDD.GetEDDRequestBuilder;
import clients.order.post.getEDD.response.GetEDDResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetEDDTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGetEDD() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        SoftReservationForCartResponseBody softReservationForCartResponseBody = new StartingStep(useCase)
                .proceedTillSoftReservationCartStep()
                .softReservationCart();

        GetEDDRequest request = new GetEDDRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withCartCode(useCase.getCartCode())
                .withUsername(useCase.getUser().getUsername())
                .withJsonRequestBody(softReservationForCartResponseBody)
                .withPinCode(product.getPinCode())
                .build();

        GetEDDResponseBody getEDDResponseBody = new GetEDDClient().getEDD(request);
        getEDDResponseBody.assertGetEDDResponse();
    }
}
