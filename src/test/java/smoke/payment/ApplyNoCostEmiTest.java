package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.carts.post.applyNoCostEmi.response.ApplyNoCostEmiResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class ApplyNoCostEmiTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToApplyNoCostEmi() throws Exception {

        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(9).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NOCOSTEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .build();

        ApplyNoCostEmiResponseBody responseBody = new StartingStep(useCase)
                .proceedTillApplyNoCostEmiStep()
                .applyNoCostEmi();

        responseBody.assertApplyNoCostEmiResponse();
    }
}
