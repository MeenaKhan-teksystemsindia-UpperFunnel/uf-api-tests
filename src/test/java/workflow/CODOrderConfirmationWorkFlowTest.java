package workflow;

import entities.Product;
import org.testng.annotations.Test;
import payments.cod.CodPayment;
import testData.productData.ProductDataService;
import util.Categories;

public class CODOrderConfirmationWorkFlowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetOrderConfirmationForCodPaymentMode() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        new CodPayment().makePayment(product);
    }
}