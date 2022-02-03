package smoke.product;

import builder.UseCaseBuilder;
import clients.products.get.reviews.ReviewsClient;
import clients.products.get.reviews.ReviewsRequest;
import clients.products.get.reviews.ReviewsRequestBuilder;
import clients.products.get.reviews.response.ReviewsResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

public class ReviewsTest {

    @Test(groups = Categories.SMOKE)
    public void ShouldBeAbleToGetReviews() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        ReviewsRequest request = new ReviewsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withProductCode(product.getProductCode())
                .build();

        ReviewsResponseBody responseBody = new ReviewsClient().getReviews(request);

        responseBody.assertGetReviewsResponse();

    }

}