package smoke.oauth;

import clients.oauth.post.globalAccessToken.GlobalAccessTokenClient;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequest;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequestBuilder;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class GlobalAccessTokenTest {

    @Test(groups = Categories.SMOKE)
    public void shouldGenerateGlobalAccessTokenCart() {

        GlobalAccessTokenRequest request = new GlobalAccessTokenRequestBuilder().build();
        GlobalAccessTokenResponseBody responseBody = new GlobalAccessTokenClient().globalAccessToken(request);

        responseBody.assertGlobalAccessTokenResponse();
    }
}