package smoke.mobilenumberlogin;

import builder.UseCaseBuilder;
import clients.mobileNumberLogin.post.validateCustomer.ValidateCustomerClient;
import clients.mobileNumberLogin.post.validateCustomer.ValidateCustomerRequest;
import clients.mobileNumberLogin.post.validateCustomer.ValidateCustomerRequestBuilder;
import clients.mobileNumberLogin.post.validateCustomer.ValidateCustomerResponseBody;
import entities.UseCase;
import entities.model;
import org.testng.annotations.Test;
import steps.StartingStep;
import util.Categories;

import java.util.UUID;

@Test(groups = Categories.SMOKE)
public class ValidateCustomerTest {

    public void ShouldBeAbleToValidateCustomer() throws Exception {

        UseCase useCase = new UseCaseBuilder().build();

        new StartingStep(useCase).proceedTillGenerateGlobalAccessTokenStep().generateGlobalAccessToken();

        model model = new model(UUID.randomUUID().toString() + "@tctv.com",
                "9731720222","123456", "password","9731720222");

        ValidateCustomerRequest request = new ValidateCustomerRequestBuilder()
                .withAuthorization(useCase.getGlobalAccessToken())
                .withModel(model)
                .build();

        ValidateCustomerResponseBody responseBody = new ValidateCustomerClient().validateCustomer(request);

        responseBody.assertValidateCustomerResponse();
    }
}
