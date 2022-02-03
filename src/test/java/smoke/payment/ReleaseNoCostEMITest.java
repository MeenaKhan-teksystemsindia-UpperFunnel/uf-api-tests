package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.releaseNoCostEMI.ReleaseNoCostEMIClient;
import clients.payment.post.releaseNoCostEMI.ReleaseNoCostEMIRequest;
import clients.payment.post.releaseNoCostEMI.ReleaseNoCostEMIRequestBuilder;
import clients.payment.post.releaseNoCostEMI.response.ReleaseNoCostEMIResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class ReleaseNoCostEMITest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleReleaseNoCostEMI() throws Exception {

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

        new StartingStep(useCase)
                .proceedTillApplyNoCostEmiStep()
                .applyNoCostEmi();

        ReleaseNoCostEMIRequest request = new ReleaseNoCostEMIRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCardGuid(useCase.getCartGuid())
                .withCartCode(useCase.getCartCode())
                .withCouponCode(useCase.getEmiDetails().getCouponCode())
                .build();

        ReleaseNoCostEMIResponseBody responseBody = new ReleaseNoCostEMIClient().releaseNoCostEMI(request);
        responseBody.assertReleaseNoCostEMIResponse();
    }
}

