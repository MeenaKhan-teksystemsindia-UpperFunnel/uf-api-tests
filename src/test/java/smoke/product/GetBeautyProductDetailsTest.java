package smoke.product;

import clients.products.get.getProductDetails.GetProductDetailsClient;
import clients.products.get.getProductDetails.GetProductDetailsRequest;
import clients.products.get.getProductDetails.GetProductDetailsRequestBuilder;
import clients.products.get.getProductDetails.response.GetProductDetailsResponseBody;
import entities.Product;
import org.testng.annotations.Test;
import services.ProductFeatureMappingService;
import testData.productData.ProductDataService;
import util.Categories;

import java.util.ArrayList;
import java.util.List;

public class GetBeautyProductDetailsTest {

    @Test(groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToGetProductDetails() throws Exception {

        List<Product> productList=new ArrayList<Product>();
        ProductFeatureMappingService service = new ProductFeatureMappingService();
        Product product1 = ProductDataService.getInstance().getBodyMistsProduct(1);
        Product product2 = ProductDataService.getInstance().getDeodrantsProduct(1);
        productList.add(product1);
        productList.add(product2);

        for (Product product : productList){
            GetProductDetailsRequest request = new GetProductDetailsRequestBuilder()
                    .withProductCode(product.getProductCode())
                    .build();

            GetProductDetailsResponseBody responseBody = new GetProductDetailsClient().getProductDetails(request);

            responseBody.assertGetProductDetailsResponse();
            service.assertBeautyAPIWithAttributeNames(responseBody, product.getProductType());
        }
    }
}
