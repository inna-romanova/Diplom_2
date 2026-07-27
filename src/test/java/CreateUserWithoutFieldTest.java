import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import utils.Credential;
import steps.UserSteps;
import utils.ErrorMessages;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserWithoutFieldTest {

    private final String email = Credential.EMAIL;
    private final String password = Credential.PASSWORD;
    private final String name = Credential.NAME;

    UserSteps userSteps = new UserSteps();

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля email.")
    @Description("Пользователь не создается")
    public void createUserWithoutEmail() {
        ValidatableResponse response = userSteps.createUser("", password, name);
        response
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.USER_REQUIRED_FIELDS_ERROR_MSG));
    }

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля password.")
    @Description("Пользователь не создается")
    public void createUserWithoutPassword() {
        ValidatableResponse response = userSteps.createUser(email, "", name);
        response
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.USER_REQUIRED_FIELDS_ERROR_MSG));
    }

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля name.")
    @Description("Пользователь не создается")
    public void createUserWithoutName() {
        ValidatableResponse response = userSteps.createUser(email, password, "");
        response
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.USER_REQUIRED_FIELDS_ERROR_MSG));
    }
}
