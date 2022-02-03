package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.getCartDetails.response.GetCartDetailsResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class GetCartDetailsTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetCartDetails() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        GetCartDetailsResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetCartDetailsStep()
                .getCartDetails();

        responseBody.assertGetCartDetailsResponse(useCase);
    }
}