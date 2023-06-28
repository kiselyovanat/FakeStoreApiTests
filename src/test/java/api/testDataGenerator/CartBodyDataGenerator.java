package api.testDataGenerator;

import api.models.cart.PostCartRequestModel;
import api.models.cart.ProductInCartModel;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CartBodyDataGenerator {
    public static LocalDate date = LocalDate.now();

    public static PostCartRequestModel generateCartBody(Integer userId) {

        ProductInCartModel product1 = new ProductInCartModel(),
                product2 = new ProductInCartModel();
        product1.setProductId("6");
        product1.setQuantity("2");
        product2.setProductId("4");
        product2.setQuantity("3");

        List<ProductInCartModel> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        PostCartRequestModel cartBody = new PostCartRequestModel();
        cartBody.setDate(date.toString());
        cartBody.setUserId(userId.toString());
        cartBody.setProducts(products);

        return cartBody;
    }
}
