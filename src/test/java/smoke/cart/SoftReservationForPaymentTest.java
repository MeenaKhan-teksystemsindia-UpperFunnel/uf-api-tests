package smoke.cart;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.softReservationForPayment.response.SoftReservationForPaymentResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.cardData.DebitCards;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class SoftReservationForPaymentTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGenerateSoftReservationForPayement() throws Exception {

        Card debitCard = new DebitCards().getDebitCard();
        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.DEBITCARD))
                .withCard(debitCard)
                .build();

        SoftReservationForPaymentResponseBody responseBody = new StartingStep(useCase)
                .proceedTillSoftReservationPaymentStep()
                .softReservationPayment();


        responseBody.assertSoftReservationForPayment(useCase);
    }
}
