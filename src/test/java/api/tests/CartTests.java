package api.tests;

import api.models.cart.CartModel;
import api.models.cart.PostCartRequestModel;
import api.models.cart.PostCartResponseModel;
import api.testDataGenerator.CartBodyDataGenerator;
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.specs.cart.DeleteCartSpec.deleteCartRequestSpec;
import static api.specs.cart.DeleteCartSpec.deleteCartResponseSpec;
import static api.specs.cart.GetSingleCartSpec.getSingleCartRequestSpec;
import static api.specs.cart.GetSingleCartSpec.getSingleCartResponseSpec;
import static api.specs.cart.PostCardSpec.postCartRequestSpec;
import static api.specs.cart.PostCardSpec.postCartResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTests {

    @Test
    @Tag("APITest")
    @Feature("FakeStoreApi - взаимодействие с корзинами")
    @DisplayName("Получение одной корзины")
    void getSingleCartTest() {
        CartModel response = step("Делаем запрос на получение корзины с id=5", () ->
                given(getSingleCartRequestSpec)
                        .when()
                        .get("/5")
                        .then()
                        .spec(getSingleCartResponseSpec)
                        .extract().as(CartModel.class));

        step("Проверяем id корзины", () ->
                assertEquals(response.getId(), "5"));
        step("Проверяем id юзера", () ->
                assertEquals(response.getUserId(), "3"));
    }

    @Test
    @Tag("APITest")
    @Feature("FakeStoreApi - взаимодействие с корзинами")
    @DisplayName("Добавление корзины юзеру")
    void addCartTest(){
        Faker faker = new Faker();

        Integer userId = faker.number().numberBetween(15, 30);

        PostCartRequestModel cartBody = CartBodyDataGenerator.generateCartBody(userId);

        PostCartResponseModel response = step("Добавляем корзину юзеру с id = " + userId, () ->
                given(postCartRequestSpec)
                        .body(cartBody)
                        .when()
                        .post()
                        .then()
                        .spec(postCartResponseSpec)
                        .extract().as(PostCartResponseModel.class));

        step("Проверяем id корзины", () ->
                assertEquals(response.getId(), "11"));
        //в документации сказано, что в ответе возвращается полный объект,
        //но по факту возвращается только id, который всегда равен 11
    }

    @Test
    @Tag("APITest")
    @Feature("FakeStoreApi - взаимодействие с корзинами")
    @DisplayName("Удаление корзины")
    void deleteCartTest() {
        CartModel response = step("Удаляем корзину", () ->
                given(deleteCartRequestSpec)
                        .when()
                        .delete("/5")
                        .then()
                        .spec(deleteCartResponseSpec)
                        .extract().as(CartModel.class));

        step("Проверяем id продукта в ответе", () ->
                assertEquals(response.getId(), "5"));
        step("Проверяем id юзера", () ->
                assertEquals(response.getUserId(), "3"));
    }
}
