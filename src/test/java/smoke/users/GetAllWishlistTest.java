package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.getAllWishlist.GetAllWishlistClient;
import clients.users.post.getAllWishlist.GetAllWishlistRequest;
import clients.users.post.getAllWishlist.GetAllWishlistRequestBuilder;
import clients.users.post.getAllWishlist.response.GetAllWishlistResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;


public class GetAllWishlistTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetAllWishlist() {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        GetAllWishlistRequest request = new GetAllWishlistRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .build();

        GetAllWishlistResponseBody responseBody = new GetAllWishlistClient().getAllWishList(request);
        responseBody.assertGetAllWishlistResponse();
    }
}
