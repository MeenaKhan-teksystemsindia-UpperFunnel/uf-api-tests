package smoke.users;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.users.post.cliqcash.applyCliqCash.response.ApplyCliqCashResponseBody;
import entities.*;
import org.testng.annotations.Test;
import payments.internetBanking.NetBankingPayment;
import steps.StartingStep;
import testData.productData.ProductDataService;
import testData.userData.UserDataService;
import util.Categories;
import util.CliqCashDataHelper;

import java.util.Arrays;

public class ApplyCliqCashTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToApplyCliqCash() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();
        User user = UserDataService.getInstance().getKYCVerifiedUser();

        double cliqCashBalance = new CliqCashDataHelper().getCliqCashBalance();

        if (cliqCashBalance == 0) {
            new NetBankingPayment().buyAndRedeemGiftCard(Bank.HDFC, 20);
        }

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withUser(user)
                .withPaymentModes(Arrays.asList(PaymentMode.CLIQCASH))
                .build();

        ApplyCliqCashResponseBody responseBody = new StartingStep(useCase)
                .proceedTillApplyCliqCashStep()
                .applyCliqCash();

        responseBody.assertApplyCliqCashResponse();
    }
}