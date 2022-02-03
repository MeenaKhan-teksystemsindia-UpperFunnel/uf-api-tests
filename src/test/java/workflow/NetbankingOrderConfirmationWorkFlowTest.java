package workflow;

import entities.Bank;
import entities.Product;
import org.testng.annotations.Test;
import payments.internetBanking.NetBankingPayment;
import testData.productData.ProductDataService;
import util.Categories;


public class NetbankingOrderConfirmationWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetOrderConfirmationForNetBankingPaymentMode() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        new NetBankingPayment().makePayment(product, Bank.HDFC);
    }
}