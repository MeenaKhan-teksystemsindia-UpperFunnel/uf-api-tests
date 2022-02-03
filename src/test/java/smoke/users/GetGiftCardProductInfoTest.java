package smoke.users;

import builder.UseCaseBuilder;
import clients.users.get.getGiftCardProductInfo.GetGiftCardProductInfoClient;
import clients.users.get.getGiftCardProductInfo.GetGiftCardProductInfoRequest;
import clients.users.get.getGiftCardProductInfo.GetGiftCardProductInfoRequestBuilder;
import clients.users.get.getGiftCardProductInfo.response.GetGiftCardProductInfoResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class GetGiftCardProductInfoTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetGiftCardProductInfo() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        GetGiftCardProductInfoRequest request = new GetGiftCardProductInfoRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        GetGiftCardProductInfoResponseBody responseBody = new GetGiftCardProductInfoClient().getGiftCardProductInfo(request);

        responseBody.assertGetGiftCardProductInfoResponse();
    }
}
