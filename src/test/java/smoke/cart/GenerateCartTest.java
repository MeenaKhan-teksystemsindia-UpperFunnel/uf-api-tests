package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.post.generateCartId.GenerateCartIdResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class GenerateCartTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGenerateCart() {

        UseCase useCase = new UseCaseBuilder().build();

        GenerateCartIdResponseBody responseBody =
                new StartingStep(useCase).proceedTillGenerateCartStep().generateCart();

        responseBody.assertGenerateCartIdResponse();
    }
}
