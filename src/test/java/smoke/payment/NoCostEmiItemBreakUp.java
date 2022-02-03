package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.get.noCostEmiItemBreakUp.NoCostEmiItemBreakUpClient;
import clients.payment.get.noCostEmiItemBreakUp.NoCostEmiItemBreakUpRequest;
import clients.payment.get.noCostEmiItemBreakUp.NoCostEmiItemBreakUpRequestBuilder;
import clients.payment.get.noCostEmiItemBreakUp.response.NoCostEmiItemBreakUpResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class NoCostEmiItemBreakUp {

    @Test(groups = Categories.SMOKE)
    public void shouldGetNoCostEmiItemBreakUp() throws Exception {

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

        NoCostEmiItemBreakUpRequest request = new NoCostEmiItemBreakUpRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartGuid(useCase.getCartGuid())
                .withCouponCode(useCase.getEmiDetails().getCouponCode())
                .build();

        NoCostEmiItemBreakUpResponseBody responseBody = new NoCostEmiItemBreakUpClient().getNoCostEmiItemBreakUp(request);
        responseBody.assertNoCostEmiItemBreakUpResponse();
    }
}
