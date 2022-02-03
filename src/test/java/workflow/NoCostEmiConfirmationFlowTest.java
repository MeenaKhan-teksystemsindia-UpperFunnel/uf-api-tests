package workflow;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.order.post.getSelectedOrderV1.response.GetSelectedOrderV1ResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.GetCartDetailsStep;
import steps.StartingStep;
import testData.cardData.EmiCreditCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class NoCostEmiConfirmationFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetOrderConfirmationForNoCostEmiPaymentMode() throws Exception {

        Card axisBankEmiCreditCard = new EmiCreditCards().getAxisBankEmiCreditCard();
        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(6).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NOCOSTEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .withCard(axisBankEmiCreditCard)
                .build();

        new StartingStep(useCase)
                .proceedTillAddToCartStep().addAllItemsToCart();

        GetSelectedOrderV1ResponseBody responseBody =
                new GetCartDetailsStep(useCase).proceedTillGetSelectedOrderV1Step().getSelectedOrderV1();

        responseBody.assertGetSelectedOrderV1Response(useCase);

        assertEquals(responseBody.getPaymentMethod(),useCase.getPaymentModeList().get(0).getPaymentMode());
    }
}
