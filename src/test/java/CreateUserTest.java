import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Credential;
import steps.UserSteps;
import utils.ErrorMessages;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {

    private static final String email = Credential.EMAIL;
    private static final String password = Credential.PASSWORD;
    private static final String name = Credential.NAME;
    private static final String uniqueEmail = Credential.UNIQUE_EMAIL;

    private static final UserSteps userSteps = new UserSteps();

    @BeforeClass
    public static void createUser() {
        userSteps.createUser(email, password, name);
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Уникальный пользователь создается без ошибок")
    public void createUniqueUser() {
        ValidatableResponse response = userSteps.createUser(uniqueEmail, password, name);
        response
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Создание существующего пользователя")
    @Description("Пользователь не создается повторно")
    public void createAlreadyExistedUser() {
        ValidatableResponse response = userSteps.createUser(email, password, name);
        response
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.EXISTED_USER_ERROR_MSG));
    }

    @AfterClass
    public static void deleteUser() {
        userSteps.deleteUser(userSteps.getToken(email, password));
        userSteps.deleteUser(userSteps.getToken(uniqueEmail, password));
    }
}
