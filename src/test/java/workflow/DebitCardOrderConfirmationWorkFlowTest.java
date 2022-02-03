package workflow;

import entities.Card;
import entities.Product;
import org.testng.annotations.Test;
import payments.card.DebitCardPayment;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

public class DebitCardOrderConfirmationWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetOrderConfirmationForDebitCardPaymentMode() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);

        new DebitCardPayment().makePayment(product, debitCard);
    }
}
