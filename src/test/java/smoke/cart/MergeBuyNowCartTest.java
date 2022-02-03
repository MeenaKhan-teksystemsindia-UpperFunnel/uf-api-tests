package smoke.cart;

import builder.UseCaseBuilder;
import clients.carts.get.expressBuy.ExpressBuyClient;
import clients.carts.get.expressBuy.ExpressBuyRequest;
import clients.carts.get.expressBuy.ExpressBuyRequestBuilder;
import clients.carts.get.expressBuy.response.ExpressBuyResponseBody;
import clients.carts.get.getCartDetails.GetCartDetailsClient;
import clients.carts.get.getCartDetails.GetCartDetailsRequest;
import clients.carts.get.getCartDetails.GetCartDetailsRequestBuilder;
import clients.carts.get.getCartDetails.response.GetCartDetailsResponseBody;
import clients.carts.get.mergeBuyNowCart.MergeBuyNowCartClient;
import clients.carts.get.mergeBuyNowCart.MergeBuyNowCartRequest;
import clients.carts.get.mergeBuyNowCart.MergeBuyNowCartRequestBuilder;
import clients.carts.get.mergeBuyNowCart.MergeBuyNowCartResponse;
import clients.carts.post.addToCart.response.AddToCartResponseBody;
import entities.Product;
import entities.UseCase;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.Arrays;

public class MergeBuyNowCartTest {

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToMergeBuyNowCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withExpressBuy(true)
                .build();

        ExpressBuyResponseBody expressBuyResponse = new StartingStep(useCase)
                .proceedTillExpressBuyStep()
                .expressBuy();

        MergeBuyNowCartRequest mergeBuyNowCartRequest = new MergeBuyNowCartRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withCartGuid(expressBuyResponse.getBuyNowCartGuid())
                .withUsername(useCase.getUser().getUsername())
                .build();

        MergeBuyNowCartResponse mergeBuyNowCartResponse = new MergeBuyNowCartClient().mergeBuyNowCart(mergeBuyNowCartRequest);
        mergeBuyNowCartResponse.assertMergeBuyNowCartResponse();
        Assert.assertFalse(mergeBuyNowCartResponse.isBuyNowCart());

        GetCartDetailsRequest getCartDetailsRequest = new GetCartDetailsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withPincode(product.getPinCode())
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(mergeBuyNowCartResponse.getBuyNowCartCode())
                .build();

        GetCartDetailsResponseBody getCartDetailsResponse = new GetCartDetailsClient().cartDetails(getCartDetailsRequest);
        getCartDetailsResponse.assertGetCartDetailsResponse(useCase);
        Assert.assertFalse(getCartDetailsResponse.isBuyNowCart());
        Assert.assertEquals(getCartDetailsResponse.getCount(), 1);
        Assert.assertEquals(getCartDetailsResponse.getCartGuid(), mergeBuyNowCartResponse.getBuyNowCartCode());

    }

    @Test(groups = Categories.SMOKE)
    public void shouldBeAbleToMergeBuyNowCartToExistingCart() throws Exception {

        Product product01 = ProductDataService.getInstance().getProduct01(1);
        Product product02 = ProductDataService.getInstance().getProduct02(1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product01))
                .build();

        AddToCartResponseBody addToCartResponse = new StartingStep(useCase)
                .proceedTillAddToCartStep()
                .addOneItemToCart();

        ExpressBuyRequest expressBuyRequest = new ExpressBuyRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withProductCode(product02.getProductCode())
                .withUssidCode(product02.getUssid()).build();

        ExpressBuyResponseBody expressBuyResponse = new ExpressBuyClient().expressBuy(expressBuyRequest);
        expressBuyResponse.assertExpressBuyResponse();
        Assert.assertTrue(expressBuyResponse.isBuyNowCart());

        MergeBuyNowCartRequest mergeBuyNowCartRequest = new MergeBuyNowCartRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withCartGuid(expressBuyResponse.getBuyNowCartGuid())
                .withUsername(useCase.getUser().getUsername())
                .build();

        MergeBuyNowCartResponse mergeBuyNowCartResponse = new MergeBuyNowCartClient().mergeBuyNowCart(mergeBuyNowCartRequest);
        mergeBuyNowCartResponse.assertMergeBuyNowCartResponse();
        Assert.assertFalse(mergeBuyNowCartResponse.isBuyNowCart());
        Assert.assertEquals(mergeBuyNowCartResponse.getBuyNowCartCode(), addToCartResponse.getCode());
        Assert.assertEquals(mergeBuyNowCartResponse.getBuyNowCartGuid(), addToCartResponse.getGuid());

        GetCartDetailsRequest getCartDetailsRequest = new GetCartDetailsRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withPincode(product02.getPinCode())
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(mergeBuyNowCartResponse.getBuyNowCartCode())
                .build();

        GetCartDetailsResponseBody getCartDetailsResponse = new GetCartDetailsClient().cartDetails(getCartDetailsRequest);
        getCartDetailsResponse.assertGetCartDetailsResponse(useCase);
        Assert.assertFalse(getCartDetailsResponse.isBuyNowCart());
        Assert.assertEquals(getCartDetailsResponse.getCount(), 2);
        Assert.assertEquals(getCartDetailsResponse.getCartGuid(), mergeBuyNowCartResponse.getBuyNowCartCode());
    }
}