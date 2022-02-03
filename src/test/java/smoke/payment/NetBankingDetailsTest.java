package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.netbankingDetails.response.NetbankingDetailsResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.NetbankingDetailsStep;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class NetBankingDetailsTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCodEligibilityCheckForOrder() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NETBANKING))
                .withBank(Bank.HDFC)
                .build();

        new StartingStep(useCase).proceedTillGetUserBearerAccessTokenStep().userBearerAccessToken();

        NetbankingDetailsResponseBody responseBody = new NetbankingDetailsStep(useCase)
                .getNetbankingDetails();

        responseBody.assertNetbankingDetailsResponse(useCase);
    }
}