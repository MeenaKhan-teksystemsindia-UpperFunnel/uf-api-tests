package workflow;

import entities.Card;
import entities.Product;
import entities.User;
import org.testng.annotations.Test;
import payments.card.SavedDebitCardPayment;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import testData.userData.UserDataService;
import util.Categories;


public class SavedDebitCardWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetExpressBuyOrderConfirmationForSavedDebitCartPaymentMode() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        User user = UserDataService.getInstance().getKYCVerifiedUser();

        new SavedDebitCardPayment().makePaymentWithUser(product, debitCard, user);
    }
}
