package smoke.users;

import builder.UseCaseBuilder;
import clients.users.post.customerLogin.response.CustomerLoginResponseBody;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

public class CustomerLoginTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoCustomerLogin() {

        UseCase useCase = new UseCaseBuilder().build();

        CustomerLoginResponseBody responseBody =
                new StartingStep(useCase).proceedTillCustomerLoginStep().customerLogin();

        responseBody.assertCustomerLoginResponse(useCase);
    }
}
