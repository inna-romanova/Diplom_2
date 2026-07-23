import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Credential;
import steps.UserSteps;

public class CreateUserTest {

    private static final String email = Credential.EMAIL;
    private static final String password = Credential.PASSWORD;
    private static final String name = Credential.NAME;
    private static final String uniqueEmail = Credential.UNIQUE_EMAIL;

    private static final UserSteps userSteps = new UserSteps();

    @BeforeClass
    public static void createUser() {
        userSteps.createUser(email, password, name, 200);
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Уникальный пользователь создается без ошибок")
    public void createUniqueUser() {
        userSteps.createUser(uniqueEmail, password, name, 200);
    }

    @Test
    @DisplayName("Создание существующего пользователя")
    @Description("Пользователь не создается повторно")
    public void createAlreadyExistedUser() {
        userSteps.createUser(email, password, name, 403);
    }

    @AfterClass
    public static void deleteUser() {
        userSteps.deleteUser(userSteps.getToken(email, password));
        userSteps.deleteUser(userSteps.getToken(uniqueEmail, password));
    }
}
