package workflow;

import entities.Bank;
import entities.Product;
import org.testng.annotations.Ignore;
import payments.internetBanking.PayPalPayment;
import testData.productData.ProductDataService;


public class PayPalOrderConfirmationWorkFlowTest {

    @Ignore
    public void shouldBeAbleToGetOrderConfirmationForPayPalPaymentMode() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        new PayPalPayment().makePayment(product, Bank.PAYPAL);
    }
}
