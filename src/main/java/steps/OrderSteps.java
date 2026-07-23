package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.RequestSpec;
import utils.Urls;
import data.OrderData;
import data.Data;
import data.GetIngredientsResponse;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderSteps extends RequestSpec {

    @Step("Create order")
    public void createOrder(ArrayList<String> ingredients, int statusCode, String bearerToken) {
        OrderData orderData = new OrderData(ingredients);
        Response response =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .headers("authorization", bearerToken)
                        .and()
                        .body(orderData)
                        .when()
                        .post(Urls.ORDER_CREATE_GET);
        response.then().statusCode(statusCode);
        if (statusCode == 200) {
            response.then().assertThat().body("success", equalTo(true));
        } else if (statusCode == 400) {
            response.then().assertThat().body("success", equalTo(false));
        } else if (statusCode == 403) {
            response.then().assertThat().body("success", equalTo(false));
        } else if (statusCode == 500) {
            response.then().assertThat().body("success", equalTo(false));
        }
    }

    @Step("Get ingredients")
    public ArrayList<String> getIngredients() {
        GetIngredientsResponse getIngredientsResponse =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .when()
                        .get(Urls.GET_INGREDIENTS)
                        .body().as(GetIngredientsResponse.class);

        List<Data> dataList = getIngredientsResponse.getData();
        ArrayList<String> ingredientsList = new ArrayList<>();
        Data data;
        for (Data value : dataList) {
            data = value;
            ingredientsList.add(data.get_id());
        }

        return ingredientsList;
    }
}
