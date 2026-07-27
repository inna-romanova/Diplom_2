package steps;

import data.LoginResponse;
import data.UserData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.RequestSpec;
import utils.Urls;

import static io.restassured.RestAssured.given;

public class UserSteps extends RequestSpec {
    @Step("Create user")
    public ValidatableResponse createUser(String email, String password, String name){
        UserData userData = new UserData(email, password, name);
        return given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .post(Urls.USER_CREATE)
                .then();
    }

    @Step("Delete user")
    public void deleteUser(String bearerToken){
        given()
                .spec(requestSpec())
                .headers("authorization", bearerToken)
                .delete(Urls.USER_GET_UPDATE_DELETE);

    }

    @Step("Login user")
    public ValidatableResponse loginUser(String email, String password){
        UserData userData = new UserData(email, password);
        return given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .post(Urls.USER_LOGIN)
                .then();
    }

    @Step("Get token")
    public String getToken(String email, String password){
        UserData userData = new UserData(email, password);
        LoginResponse loginResponse =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(Urls.USER_LOGIN)
                        .body().as(LoginResponse.class);
        return  loginResponse.getAccessToken();
    }
}
