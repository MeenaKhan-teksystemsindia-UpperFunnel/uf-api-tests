package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.bundlesPrices.BundledPricesClient;
import clients.users.post.bundlesPrices.BundledPricesRequestBuilder;
import clients.users.post.bundlesPrices.BundlingPricesRequest;
import clients.users.post.bundlesPrices.response.BundlingPricesResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;

import java.util.Arrays;


public class BundlingPricesTest {

    @Test
    public void bundlingPriceTest() throws Exception {

        Product suggestedProduct = ProductDataService.getInstance().getSuggestedProduct(1);
        Product associatedProduct = ProductDataService.getInstance().getAssociatedProduct(1);;
        UseCase useCase = new UseCaseBuilder().withProducts(Arrays.asList(suggestedProduct)).build();

        new StartingStep(useCase)
                .proceedTillGetUserBearerAccessTokenStep()
                .userBearerAccessToken();

        BundlingPricesRequest bundlingPricesRequest
                = new BundledPricesRequestBuilder()
                .withWrapperItems(suggestedProduct, associatedProduct)
                .build();

        BundlingPricesResponseBody responseBody = new BundledPricesClient()
                .bundlingPrices(bundlingPricesRequest);

        responseBody.assertProductBundlingAdditionResponseBody();
    }
}
