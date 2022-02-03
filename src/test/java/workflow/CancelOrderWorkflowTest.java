package workflow;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.order.get.getSelectedTransaction.response.GetSelectedTransactionResponseBody;
import clients.order.post.getSelectedOrderV1.response.GetSelectedOrderV1ResponseBody;
import entities.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import responses.OrderStatus;
import services.CancelOrderService;
import services.GetOrderDetailsService;
import steps.StartingStep;
import testData.cardData.CreditCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class CancelOrderWorkflowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToCancelTheOrder() throws Exception {

        Card creditCard = new CreditCards().getCreditCard();
        Product product = ProductDataService.getInstance().getProduct01(2);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.CREDITCARD))
                .withCard(creditCard)
                .build();

        GetSelectedOrderV1ResponseBody selectedOrder = new StartingStep(useCase).proceedTillGetSelectedOrderV1Step().getSelectedOrderV1();

        Thread.sleep(50000);

        new CancelOrderService().cancelTheOrderAndInitiateRefund(useCase, selectedOrder);
        Thread.sleep(25000);
        GetSelectedTransactionResponseBody orderDetailsResponse = new GetOrderDetailsService().getOrderDetails(useCase, selectedOrder);
        Assert.assertEquals(orderDetailsResponse.getProducts().get(0).getStatusDisplay(), OrderStatus.REFUNDINITIATED.getOrderStatus());
    }
}
