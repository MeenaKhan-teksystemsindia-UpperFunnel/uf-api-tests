package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.addProductInWishlist.AddProductInWishlistClient;
import clients.users.post.addProductInWishlist.AddProductInWishlistRequest;
import clients.users.post.addProductInWishlist.AddProductInWishlistRequestBuilder;
import clients.users.post.addProductInWishlist.AddProductInWishlistResponseBody;
import clients.users.post.getAllWishlist.GetAllWishlistClient;
import clients.users.post.getAllWishlist.GetAllWishlistRequest;
import clients.users.post.getAllWishlist.GetAllWishlistRequestBuilder;
import clients.users.post.getAllWishlist.response.GetAllWishlistResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

public class AddProductInWishlistTest {

    //Product already in wishlist to be removed as we are using existing user
    @Test(groups = Categories.TODO)
    public void shouldBeAbleToAddProductInWishlist() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        AddProductInWishlistRequest request = new AddProductInWishlistRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withUssid(product.getUssid())
                .withProductCode(product.getProductCode())
                .build();

        AddProductInWishlistResponseBody responseBody = new AddProductInWishlistClient().addProductInWishList(request);
        responseBody.assertAddAProductInWishlistResponse();

        GetAllWishlistRequest getAllWishlistRequest = new GetAllWishlistRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUserName(useCase.getUser().getUsername())
                .build();

        GetAllWishlistResponseBody getAllWishlistResponseBody = new GetAllWishlistClient().getAllWishList(getAllWishlistRequest);
        getAllWishlistResponseBody.assertGetAllWishlistResponse(product);
    }
}
