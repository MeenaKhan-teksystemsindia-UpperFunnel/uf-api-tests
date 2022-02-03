package workflow;

import builder.EmiDetailsBuilder;
import entities.Bank;
import entities.Card;
import entities.EmiDetails;
import entities.Product;
import org.testng.annotations.Test;
import payments.emi.StandardEmiPayment;
import testData.cardData.EmiCreditCards;
import testData.productData.ProductDataService;
import util.Categories;


public class StandardEmiExpressBuyOrderConfirmationWorkflowTest {

    @Test(groups = Categories.WORKFLOW)
    public void shouldBeAbleToGetExpressBuyOrderConfirmationForStandardEmiPaymentMode() throws Exception {

        Card axisBankEmiCreditCard = new EmiCreditCards().getAxisBankEmiCreditCard();
        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(6).build();

        new StandardEmiPayment().makePaymentExpressBuy(product, Bank.AXIS, emiDetails, axisBankEmiCreditCard);
    }
}
