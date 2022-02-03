package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.post.createElectronicsGiftCardCartGuid.CreateElectronicsGiftCardCartGuidResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class CreateElectronicsGiftCardCartGuidTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCreateElectronicsGiftCardCartGuidForGiftCard() throws Exception {

        Product giftCard = ProductDataService.getInstance().getGiftCard(1, 20);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(giftCard))
                .withDeliveryAddress(address)
                .build();

        CreateElectronicsGiftCardCartGuidResponseBody responseBody = new StartingStep(useCase)
                .proceedTillCreateElectronicsGiftCardCartGuidStep().createElectronicsGiftCardCartGuid();

        responseBody.assertCreateElectronicsGiftCardCartGuidResponse();
    }

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCreateElectronicsGiftCardCartGuidForTopUp() throws Exception {

        Product topUp = ProductDataService.getInstance().getTopUpProduct(1, 20);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(topUp))
                .withDeliveryAddress(address)
                .build();

        CreateElectronicsGiftCardCartGuidResponseBody responseBody = new StartingStep(useCase)
                .proceedTillCreateElectronicsGiftCardCartGuidStep().createElectronicsGiftCardCartGuid();

        responseBody.assertCreateElectronicsGiftCardCartGuidResponse();
    }
}
