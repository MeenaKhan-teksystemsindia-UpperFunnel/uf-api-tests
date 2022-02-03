package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.displayOrderSummary.response.DisplayOrderSummaryResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class DisplayOrderSummaryTest {

    @Test(groups = Categories.SMOKE)
    public void shouldDisplayOrderSummary() throws Exception {

        Product product = ProductDataService.getInstance()
                .getProduct01(1);

        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        DisplayOrderSummaryResponseBody responseBody = new StartingStep(useCase).proceedTillDisplayOrderSummaryStep()
                .displayOrderSummary();

        responseBody.assertDisplayOrderSummaryResponse(useCase);
    }
}