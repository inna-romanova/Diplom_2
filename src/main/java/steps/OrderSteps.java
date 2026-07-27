package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.RequestSpec;
import utils.Urls;
import data.OrderData;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps extends RequestSpec {

    @Step("Create order")
    public ValidatableResponse createOrder(ArrayList<String> ingredients, String bearerToken) {
        OrderData orderData = new OrderData(ingredients);
        return given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .headers("authorization", bearerToken)
                .body(orderData)
                .when()
                .post(Urls.ORDER_CREATE_GET)
                .then();
    }

    @Step("Get ingredients")
    public ArrayList<String> getIngredients() {
        List<String> ids = given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .when()
                .get(Urls.GET_INGREDIENTS)
                .then()
                .extract()
                .path("data._id");

        return new ArrayList<>(ids);
    }
}
