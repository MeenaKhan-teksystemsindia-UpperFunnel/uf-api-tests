package workflow;

import entities.Card;
import entities.Product;
import entities.User;
import org.testng.annotations.Test;
import payments.card.SavedCreditCardPayment;
import testData.cardData.CreditCards;
import testData.productData.ProductDataService;
import testData.userData.UserDataService;
import util.Categories;


public class SavedCreditCardWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetOrderConfirmationForSavedCreditCardPaymentMode() throws Exception {

        Card creditCard = new CreditCards().getCreditCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        User user = UserDataService.getInstance().getKYCVerifiedUser();

        new SavedCreditCardPayment().makePaymentWithUser(product, creditCard, user);
    }
}
