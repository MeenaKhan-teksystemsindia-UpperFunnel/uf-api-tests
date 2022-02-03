package smoke.pincode;

import clients.oauth.post.globalAccessToken.GlobalAccessTokenClient;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequest;
import clients.oauth.post.globalAccessToken.GlobalAccessTokenRequestBuilder;
import clients.pincode.get.pincodeData.PincodeDataClient;
import clients.pincode.get.pincodeData.PincodeDataRequest;
import clients.pincode.get.pincodeData.PincodeDataRequestBuilder;
import clients.pincode.get.pincodeData.response.PincodeDataResponseBody;
import entities.Product;
import org.testng.annotations.Test;
import testData.productData.ProductDataService;
import util.Categories;

public class PincodeDataTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToGetPincodeData() throws Exception {

        GlobalAccessTokenRequest globalAccessTokenRequest = new GlobalAccessTokenRequestBuilder().build();
        String globalAccessToken = new GlobalAccessTokenClient()
                .globalAccessToken(globalAccessTokenRequest)
                .getAccessToken();

        Product product = ProductDataService.getInstance().getProduct01(1);


        PincodeDataRequest request = new PincodeDataRequestBuilder()
                .withPincode(product.getPinCode())
                .withGlobalAccessToken(globalAccessToken)
                .build();

        PincodeDataResponseBody responseBody = new PincodeDataClient().getPincodeData(request);

        responseBody.assertPincodeDataResponse(request.getPincode());
    }
}
