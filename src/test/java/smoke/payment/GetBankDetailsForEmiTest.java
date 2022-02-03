package smoke.payment;

import builder.AddressBuilder;
import builder.EmiDetailsBuilder;
import builder.UseCaseBuilder;
import clients.payment.get.bankDetailsForEmi.response.GetBankDetailsForEmiResponseBody;
import entities.*;
import org.testng.annotations.Test;
import responses.errors.ErrorResponse;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_OK;

public class GetBankDetailsForEmiTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetBankDetailsForStandardEmi() throws Exception {

        Product product = ProductDataService.getInstance().getNoCostEmiProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(9).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.STANDARDEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .build();

        GetBankDetailsForEmiResponseBody responseBody = new StartingStep(useCase)
                .proceedTillGetBankDetailsForEmiStep()
                .getBankDetailsForEmi();

        responseBody.assertGetBankDetailsForEmiResponse();
    }

    @Test(groups = Categories.SMOKE)
    public void shouldThrowErrorForNonAssociationFineJewelleryProduct() throws Exception {

        Product product = ProductDataService.getInstance().getNonVariantFineJewelleryProduct(5);
        Address address = new AddressBuilder().build();
        EmiDetails emiDetails = new EmiDetailsBuilder().withDesiredEmiTenure(6).build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .withPaymentModes(Arrays.asList(PaymentMode.STANDARDEMI))
                .withBank(Bank.AXIS)
                .withEmiDetails(emiDetails)
                .build();

        ErrorResponse response = new StartingStep(useCase)
                .proceedTillGetBankDetailsForEmiStep()
                .getBankDetailsForEmiExpectingError();

        response.assertErrorResponse("emiBankListWsDTO"
                , "One or more products are not eligible for EMI, please use another payment method to make your purchase."
                , "Failure"
                , SC_OK);
    }
}
