package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.voucherSequence.VoucherSequenceClient;
import clients.carts.get.voucherSequence.VoucherSequenceRequest;
import clients.carts.get.voucherSequence.VoucherSequenceRequestBuilder;
import clients.carts.get.voucherSequence.response.VoucherSequenceResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;

public class VocherSequenceTest {

    @Test
    public void ShouldBeAbleToGetVocherSequence() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        VoucherSequenceRequest request = new VoucherSequenceRequestBuilder()
                .withBearerToken(useCase.getGlobalAccessToken())
                .build();

        VoucherSequenceResponseBody responseBody = new VoucherSequenceClient().getVoucherSequenceList(request);

        responseBody.assertVocherSequenceResponse();

    }
}
