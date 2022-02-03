package resource;

import builder.AddressBuilder;
import builder.UseCaseBuilder;
import clients.carts.get.cartDetailsCnc.CartDetailsCncClient;
import clients.carts.get.cartDetailsCnc.CartDetailsCncRequest;
import clients.carts.get.cartDetailsCnc.CartDetailsCncRequestBuilder;
import clients.carts.get.cartDetailsCnc.response.CartDetailsCncResponseBody;
import clients.carts.post.addToCart.AddToCartClient;
import clients.carts.post.addToCart.AddToCartRequest;
import clients.carts.post.addToCart.AddToCartRequestBuilder;
import clients.carts.post.addToCart.response.AddToCartResponseBody;
import clients.carts.post.updateCart.UpdateCartClient;
import clients.carts.post.updateCart.UpdateCartRequest;
import clients.carts.post.updateCart.UpdateCartRequestBuilder;
import clients.carts.post.updateCart.response.UpdateCartResponseBody;
import entities.Address;
import entities.Product;
import entities.UseCase;
import org.testng.annotations.Test;
import steps.StartingStep;
import testData.productData.ProductDataService;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;

public class CartResourceTests {

    @Test
    public void shouldBeAbleToAddProductInCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);
        Address address = new AddressBuilder().build();

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .withDeliveryAddress(address)
                .build();

        new StartingStep(useCase).proceedTillAddToCartStep().addAllItemsToCart();

        // 1. Add one more quantity
        AddToCartRequest request = new AddToCartRequestBuilder()
                .withProduct(product)
                .withBearerToken(UseCase.bearerToken)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .build();

        AddToCartResponseBody responseBody = new AddToCartClient().addToCart(request);

        Product product1 =
                useCase.getProductList().stream().filter(x -> x.getProductCode().equals(product.getProductCode())).findAny().get();

        product1.setQuantityInCart(product1.getQuantityInCart()+product.getQuantity());

        responseBody.assertAddToCartResponse(useCase);


        // 2. Assert Quantity
        CartDetailsCncRequest cncRequest = new CartDetailsCncRequestBuilder()
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .withBearerToken(UseCase.bearerToken)
                .build();


        CartDetailsCncResponseBody cartDetailsCncResponseBody = new CartDetailsCncClient().cartDetailsCnc(cncRequest);
        int actualQuantity = Integer.parseInt(cartDetailsCncResponseBody.getProducts().stream().
                filter(x -> x.getProductCode().equals(product.getProductCode())).findAny().get().getQtySelectedByUser());

        assertEquals(actualQuantity, 2);
    }


    @Test
    public void shouldBeAbleToUpdateProductQuantityInCart() throws Exception {

        Product product = ProductDataService.getInstance().getProduct01(1);

        UseCase useCase = new UseCaseBuilder()
                .withProducts(Arrays.asList(product))
                .build();

        new StartingStep(useCase)
                .proceedTillAddToCartStep()
                .addOneItemToCart();

        // 1. Update product quantity in the cart
        UpdateCartRequest request = new UpdateCartRequestBuilder()
                .withBearerToken(UseCase.bearerToken)
                .withQuantity(product.getQuantity() + 4)
                .withUsername(useCase.getUser().getUsername())
                .withCartCode(useCase.getCartCode())
                .withProductIndex(0)
                .build();

        UpdateCartResponseBody updateCartResponseBody = new UpdateCartClient().updateCart(request);
        assertEquals(updateCartResponseBody.getHttpStatusCode(), SC_OK);
        int actualQuantity = Integer.parseInt(updateCartResponseBody.getProducts().get(0).getQtySelectedByUser());
        assertEquals(actualQuantity, product.getQuantity() + 4);
    }

    // CRUD operations on cart (resource) - Update cart Quantity, Delete, Add, Get etc...
}
