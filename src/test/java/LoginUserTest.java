import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Credential;
import steps.UserSteps;
import utils.ErrorMessages;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {
    private final String email = Credential.EMAIL;
    private final String password = Credential.PASSWORD;
    private final String name = Credential.NAME;
    private final String random = Credential.RANDOM;

    UserSteps userSteps = new UserSteps();

    @Before
    public void createUser() {
        userSteps.createUser(email, password, name);
    }

    @Test
    @DisplayName("Логин пользователя")
    @Description("Авторизация успешна")
    public void loginUser() {
        ValidatableResponse response = userSteps.loginUser(email, password);
        response
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Логин пользователя с неверным логином")
    @Description("Авторизация не происходит")
    public void loginUserWitWrongLogin() {
        ValidatableResponse response = userSteps.loginUser(email + random, password);
        response
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INVALID_USER_LOGIN_DATA_ERROR_MSG));
    }

    @Test
    @DisplayName("Логин пользователя с неверным паролем")
    @Description("Авторизация не происходит")
    public void loginUserWitWrongPassword() {
        ValidatableResponse response = userSteps.loginUser(email, password + random);
        response
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ErrorMessages.INVALID_USER_LOGIN_DATA_ERROR_MSG));
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(userSteps.getToken(email, password));
    }
}
