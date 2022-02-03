package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.get.orderHistoryListV1.OrderHistoryListV1Client;
import clients.users.get.orderHistoryListV1.OrderHistoryListV1Request;
import clients.users.get.orderHistoryListV1.OrderHistoryListV1RequestBuilder;
import clients.users.get.orderHistoryListV1.response.OrderHistoryListV1ResponseBody;
import entities.Address;
import entities.PaymentMode;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;
import java.util.Calendar;

public class OrderHistoryListTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleGetOrderHistoryList() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase)
                .proceedTillOrderConfirmationStep()
                .orderConfirmation();

        OrderHistoryListV1Request request = new OrderHistoryListV1RequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .build();

        OrderHistoryListV1ResponseBody responseBody = new OrderHistoryListV1Client().getOrderHistoryList(request);

        responseBody.assertOrderHistoryListResponse(useCase.getOrderId(), 1);
    }

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleGetOrderHistoryListByYear() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.COD))
                .build();

        new StartingStep(useCase)
                .proceedTillOrderConfirmationStep()
                .orderConfirmation();

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        OrderHistoryListV1Request request = new OrderHistoryListV1RequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .withOrderYear(year)
                .build();

        OrderHistoryListV1ResponseBody responseBody = new OrderHistoryListV1Client().getOrderHistoryList(request);

        responseBody.assertOrderHistoryListByYearResponse(year, 1);
    }
}
