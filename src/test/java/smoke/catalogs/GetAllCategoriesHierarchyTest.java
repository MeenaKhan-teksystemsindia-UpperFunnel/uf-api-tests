package smoke.catalogs;

import clients.catalogs.get.getAllCategorieshierarchy.GetAllCategoriesHierarchyClient;
import clients.catalogs.get.getAllCategorieshierarchy.response.GetAllCategoriesHierarchyResponseBody;
import org.testng.annotations.Test;
import util.Categories;

public class GetAllCategoriesHierarchyTest {

    @Test(groups = {Categories.SMOKE, Categories.PROD})
    public void shouldBeAbleToGetAllCategoriesHierarchy() {

        GetAllCategoriesHierarchyResponseBody responseBody = new GetAllCategoriesHierarchyClient().getAllCategoriesHierarchy();
        responseBody.assertGetAllCategoriesHierarchyResponse();
    }
}