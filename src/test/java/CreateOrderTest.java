import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Credential;
import steps.OrderSteps;
import steps.UserSteps;
import utils.ErrorMessages;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {
    private final String email = Credential.EMAIL;
    private final String password = Credential.PASSWORD;
    private final String name = Credential.NAME;
    private final ArrayList<String> emptyIngredients = new ArrayList<>();
    private final String invalidIngredientHash = "60d3b41abdacab0026a733c6";

    UserSteps userSteps = new UserSteps();
    OrderSteps orderSteps = new OrderSteps();

    @Before
    public void createUser() {
        userSteps.createUser(email, password, name);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингредиентами")
    @Description("Заказ создается")
    public void createOrderWithIngredientsByLoggedUser() {
        String token = userSteps.getToken(email, password);
        ArrayList<String> ingredients = orderSteps.getIngredients();
        System.out.println("Ingredients: " + ingredients);
        ValidatableResponse response = orderSteps.createOrder(ingredients, token);
        response
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с ингредиентами")
    @Description("Заказ создается")
    public void createOrderWithIngredientsByNotLoggedUser() {
        ArrayList<String>  ingredients = orderSteps.getIngredients();
        ValidatableResponse response = orderSteps.createOrder(ingredients, "");
        response
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без ингредиентов")
    @Description("Заказ создается")
    public void createOrderWithoutIngredientsByLoggedUser() {
        String token = userSteps.getToken(email, password);
        ValidatableResponse response = orderSteps.createOrder(emptyIngredients, token);
        response
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INGREDIENT_REQUIRED_ERROR_MSG));
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем без ингредиентов")
    @Description("Заказ не создается")
    public void createOrderWithoutIngredientsByNotLoggedUser() {
        ValidatableResponse response = orderSteps.createOrder(emptyIngredients, "");
        response
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INGREDIENT_REQUIRED_ERROR_MSG));
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингредиентом с неверным хешем")
    @Description("Заказ не создается")
    public void createOrderWithWrongHashIngredientByLoggedUser() {
        String token = userSteps.getToken(email, password);
        ArrayList<String> ingredients = new ArrayList<>(List.of(invalidIngredientHash));
        ValidatableResponse response = orderSteps.createOrder(ingredients, token);
        response
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INVALID_INGREDIENT_HASH_ERROR_MSG));
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с ингредиентом с неверным хешем")
    @Description("Заказ не создается")
    public void createOrderWithWrongHashIngredientByNotLoggedUser() {
        ArrayList<String> ingredients = new ArrayList<>(List.of(invalidIngredientHash));
        ValidatableResponse response = orderSteps.createOrder(ingredients, "");
        response
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INVALID_INGREDIENT_HASH_ERROR_MSG));
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(userSteps.getToken(email, password));
    }
}
