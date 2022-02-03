package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.createWishlist.CreateWishlistClient;
import clients.users.post.createWishlist.CreateWishlistRequest;
import clients.users.post.createWishlist.CreateWishlistRequestBuilder;
import clients.users.post.createWishlist.CreateWishlistResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class CreateWishlistTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToCreateWishlist() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        CreateWishlistRequest request = new CreateWishlistRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .build();

        CreateWishlistResponseBody responseBody = new CreateWishlistClient().createWishlist(request);

        responseBody.assertCreateWishlistResponse();
    }
}