package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.productBundlingAdditionToCart.ProductBundlingAdditionRequestBuilder;
import clients.users.post.productBundlingAdditionToCart.ProductBundlingAdditionToCartClient;
import clients.users.post.productBundlingAdditionToCart.request.ProductBundlingPricesRequest;
import clients.users.post.productBundlingAdditionToCart.response.ProductBundlingAdditionResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Ignore;
import steps.StartingStep;
import testData.productData.ProductDataService;

import java.util.Arrays;


public class ProductBundlingAdditionToCartTest {

    @Ignore
    public void productBundlingAdditionTest() throws Exception {

        Product suggestedProduct = ProductDataService.getInstance().getSuggestedProduct(1);
        Product associatedProduct = ProductDataService.getInstance().getAssociatedProduct(1);
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(suggestedProduct)).build();

        new StartingStep(useCase)
                .proceedTillGetUserBearerAccessTokenStep()
                .userBearerAccessToken();

        ProductBundlingPricesRequest productBundlingAdditionRequest
                = new ProductBundlingAdditionRequestBuilder()
                .withAccessToken(useCase.getGlobalAccessToken())
                .withCartCode(useCase.getCartCode())
                .withWrapperItems(suggestedProduct, associatedProduct)
                .build();

        ProductBundlingAdditionResponseBody responseBody = new ProductBundlingAdditionToCartClient()
                .productBundlingAdditionToCart(productBundlingAdditionRequest);

        responseBody.assertProductBundlingAdditionResponseBody();
    }
}
