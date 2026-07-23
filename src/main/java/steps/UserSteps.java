package steps;

import data.LoginResponse;
import data.UserData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.RequestSpec;
import utils.Urls;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserSteps extends RequestSpec {



    @Step("Create user")
    public void createUser(String email, String password, String name, int statusCode){
        UserData userData = new UserData(email, password, name);
        Response response =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(Urls.USER_CREATE);
        response.then().statusCode(statusCode);
        if(statusCode == 200) {
            response.then().assertThat().body("success", equalTo(true));
        } else if (statusCode == 403) {
            response.then().assertThat().body("success", equalTo(false));
        }
    }


    @Step("Delete user")
    public void deleteUser(String bearerToken){
        given()
                .spec(requestSpec())
                .headers("authorization", bearerToken)
                .delete(Urls.USER_GET_UPDATE_DELETE);

    }

    @Step("Login user")
    public void loginUser(String email, String password, int statusCode){
        UserData userData = new UserData(email, password);
        Response response =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(Urls.USER_LOGIN);
        response.then().statusCode(statusCode);
        if(statusCode == 200) {
            response.then().assertThat().body("success", equalTo(true));
        } else if (statusCode == 401) {
            response.then().assertThat().body("success", equalTo(false));
        }
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
