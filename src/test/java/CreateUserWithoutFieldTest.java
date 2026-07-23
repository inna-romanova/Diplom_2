import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import utils.Credential;
import steps.UserSteps;

public class CreateUserWithoutFieldTest {

    private final String email = Credential.EMAIL;
    private final String password = Credential.PASSWORD;
    private final String name = Credential.NAME;

    UserSteps userSteps = new UserSteps();

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля email.")
    @Description("Пользователь не создается")
    public void createUserWithoutEmail() {
        userSteps.createUser("", password, name, 403);
    }

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля password.")
    @Description("Пользователь не создается")
    public void createUserWithoutPassword() {
        userSteps.createUser(email, "", name, 403);
    }

    @Test
    @DisplayName("Создать пользователя без указания обязательного поля name.")
    @Description("Пользователь не создается")
    public void createUserWithoutName() {
        userSteps.createUser(email, password, "", 403);
    }
}
