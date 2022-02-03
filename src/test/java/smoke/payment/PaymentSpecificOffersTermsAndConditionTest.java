package smoke.payment;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.payment.get.paymentSpecificOffersTermsAndCondition.PaymentSpecificOffersTermsAndConditionClient;
import clients.payment.get.paymentSpecificOffersTermsAndCondition.response.PaymentSpecificOffersTermsAndConditionResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class PaymentSpecificOffersTermsAndConditionTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetPaymentSpecificOffersWithTermsAndConditions() throws Exception {

        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase)
                .proceedTillGetPaymentModesStep().getPaymentModes();

        PaymentSpecificOffersTermsAndConditionResponseBody responseBody = new PaymentSpecificOffersTermsAndConditionClient().getPaymentSpecificOffersTermsAndCondition();
        responseBody.assertPaymentSpecificOffersTermsAndConditionResponse();
    }
}
