package workflow;

import entities.Bank;
import entities.Card;
import entities.Product;
import org.testng.annotations.Test;
import payments.card.DebitCardPayment;
import payments.internetBanking.NetBankingPayment;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

public class SplitCliqCashDebitCardExpressBuyWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToPlaceExpressBuyOrderViaSplitCliQCashDebitCardPaymentMode() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        new NetBankingPayment().buyAndRedeemGiftCard(Bank.HDFC, 20);

        Product product = ProductDataService.getInstance().getNoCostEmiProduct(1);
        new DebitCardPayment().makeSplitCliqCashPayment_ExpressBuy(product, debitCard);
    }
}