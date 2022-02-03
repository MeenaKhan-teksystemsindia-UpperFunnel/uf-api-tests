package businessRules;

import builder.UseCaseBuilder;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import responses.errors.ErrorResponse;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class AddToCartBusinessValidationTests {

    @Test(groups = Categories.BUSINESS_VALIDATION)
    public void shouldThrowErrorWhenMoreItemsAddedToCart() throws Exception {

        int maxProductQuantity = 5;

        Product product = ProductDataService.getInstance()
                .getProduct01(maxProductQuantity + 1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        ErrorResponse response = new StartingStep(useCase)
                .proceedTillAddToCartStep()
                .addToCartExpectingError();

        String errorMessage = "You are about to exceed the max quantity.";
        response.assertErrorResponse("webSerResponseWsDTO", errorMessage, "Failure", 200);
    }
}
