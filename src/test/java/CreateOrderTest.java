import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Credential;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.ArrayList;
import java.util.List;

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
        userSteps.createUser(email, password, name, 200);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингредиентами")
    @Description("Заказ создается")
    public void createOrderWithIngredientsByLoggedUser() {
        String token = userSteps.getToken(email, password);
        ArrayList<String> ingredients = orderSteps.getIngredients();
        orderSteps.createOrder(ingredients, 200, token);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с ингредиентами")
    @Description("Заказ создается")
    public void createOrderWithIngredientsByNotLoggedUser() {
        ArrayList<String>  ingredients = orderSteps.getIngredients();
        orderSteps.createOrder(ingredients, 200, "");
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без ингредиентов")
    @Description("Заказ создается")
    public void createOrderWithoutIngredientsByLoggedUser() {
        String token = userSteps.getToken(email, password);
        orderSteps.createOrder(emptyIngredients, 400, token);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем без ингредиентов")
    @Description("Заказ не создается")
    public void createOrderWithoutIngredientsByNotLoggedUser() {
        orderSteps.createOrder(emptyIngredients, 400, "");
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингредиентом с неверным хешем")
    @Description("Заказ не создается")
    public void createOrderWithWrongHashIngredientByLoggedUser() {
        String token = userSteps.getToken(email, password);
        ArrayList<String> ingredients = new ArrayList<>(List.of(invalidIngredientHash));
        orderSteps.createOrder(ingredients, 400, token);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с ингредиентом с неверным хешем")
    @Description("Заказ не создается")
    public void createOrderWithWrongHashIngredientByNotLoggedUser() {
        ArrayList<String> ingredients = new ArrayList<>(List.of(invalidIngredientHash));
        orderSteps.createOrder(ingredients, 400, "");
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(userSteps.getToken(email, password));
    }
}
