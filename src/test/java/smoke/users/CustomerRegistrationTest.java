package smoke.users;

import clients.oauth.post.globalAccessToken.GlobalAccessTokenClient;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequest;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequestBuilder;
import clients.users.post.registration.customerRegistration.CustomerRegistrationClient;
import clients.users.post.registration.customerRegistration.CustomerRegistrationRequest;
import clients.users.post.registration.customerRegistration.CustomerRegistrationRequestBuilder;
import clients.users.post.registration.customerRegistration.response.CustomerRegistrationResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class CustomerRegistrationTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToDoCustomerRegistration() {

        GlobalAccessTokenRequest globalAccessTokenRequest = new GlobalAccessTokenRequestBuilder().build();
        String globalAccessToken = new GlobalAccessTokenClient()
                .globalAccessToken(globalAccessTokenRequest)
                .getAccessToken();

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequestBuilder()
                        .withGlobalAccessToken(globalAccessToken)
                        .build();

        CustomerRegistrationResponseBody responseBody =
                new CustomerRegistrationClient().customerRegistration(customerRegistrationRequest);

        responseBody.assertCustomerRegistrationResponse();
    }
}
