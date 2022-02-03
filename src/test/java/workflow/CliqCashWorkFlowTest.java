package workflow;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.order.post.getSelectedOrderV1.response.GetSelectedOrderV1ResponseBody;
import clients.products.get.getProductDetails.GetProductDetailsClient;
import clients.products.get.getProductDetails.GetProductDetailsRequest;
import clients.products.get.getProductDetails.GetProductDetailsRequestBuilder;
import clients.products.get.getProductDetails.response.GetProductDetailsResponseBody;
import clients.users.post.cliqcash.redeemCliqVoucher.RedeemCliqVoucherResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import testData.userData.UserDataService;
import util.Categories;

import java.util.Arrays;

public class CliqCashWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToPlaceOrderViaCliQCashPaymentMode() throws Exception {

        buyAndRedeemGiftCardToAddCliqCashBalance();

        Product product = ProductDataService.getInstance().getCliqCashProduct(1);
        Address address = new AddressBuilder().build();
        User user = UserDataService.getInstance().getKYCVerifiedUser();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withUser(user)
                .withPaymentModes(Arrays.asList(PaymentMode.CLIQCASH))
                .build();

        GetSelectedOrderV1ResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetSelectedOrderV1Step()
                .getSelectedOrderV1();

        responseBody.assertGetSelectedOrderV1Response(useCase);
    }

    public void buyAndRedeemGiftCardToAddCliqCashBalance() throws Exception {

        Product product = ProductDataService.getInstance().getCliqCashProduct(1);

        GetProductDetailsRequest request = new GetProductDetailsRequestBuilder()
                .withProductCode(product.getProductCode()).build();
        GetProductDetailsResponseBody responseBody = new GetProductDetailsClient()
                .getProductDetails(request);
        int giftCardAmount = (int) responseBody.getWinningSellerPrice().getDoubleValue() + 100;

        Product giftCard = ProductDataService.getInstance().getGiftCard(1, giftCardAmount);
        Address address = new AddressBuilder().build();
        Card debitCard = new DebitCards().getDebitCard();
        User user = UserDataService.getInstance().getKYCVerifiedUser();

        UseCase giftCardUseCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(giftCard))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .withCard(debitCard)
                .withUser(user)
                .build();

        RedeemCliqVoucherResponseBody redeemCliqVoucherResponseBody =
                new StartingStep(giftCardUseCase)
                        .proceedTillRedeemCliqVoucherStep().redeemCliqVoucher();
        redeemCliqVoucherResponseBody.assertRedeemCliqVoucherResponse();
    }
}