package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.post.noCostEmiTenureList.response.NoCostEmiTenureListResponseBody;
import entities.*;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class NoCostEmiTenureListTest {

    //@Test(groups = Categories.REGRESSION)
    public void shouldBeAbleToGetNoCostEmiTenureList() throws Exception {

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

        NoCostEmiTenureListResponseBody responseBody = new StartingStep(useCase)
                .proceedTillNoCostEmiTenureListStep()
                .noCostEmiTenureList();

        responseBody.assertNoCostEmiTenureListResponse();
    }
}
