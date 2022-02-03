package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.get.noCostEmiTnC.NoCostEmiTnCClient;
import clients.payment.get.noCostEmiTnC.NoCostEmiTnCRequest;
import clients.payment.get.noCostEmiTnC.NoCostEmiTnCRequestBuilder;
import clients.payment.get.noCostEmiTnC.response.NoCostEmiTnCResponseBody;
import clients.payment.post.noCostEmiTenureList.response.NoCostEmiTenureListResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class NoCostEmiTnCTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetNoCostEmiTnC() throws Exception {

        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(6).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.NOCOSTEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .build();

        NoCostEmiTenureListResponseBody noCostEmiTenureListResponseBody = new StartingStep(useCase)
                .proceedTillNoCostEmiTenureListStep()
                .noCostEmiTenureList();

        NoCostEmiTnCRequest request = new NoCostEmiTnCRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCode(noCostEmiTenureListResponseBody.getBankList().get(0).getCode())
                .build();

        NoCostEmiTnCResponseBody responseBody = new NoCostEmiTnCClient().getNoCostEmiTnC(request);
        responseBody.assertNoCostEmiTermsAndConditionResponse();
    }
}


