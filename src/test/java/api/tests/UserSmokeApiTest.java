package api.tests;

import api.base.BaseApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserSmokeApiTest extends BaseApi {

    private static String token;
    private static String email = "user" + System.currentTimeMillis() + "@test.com";
    private static String password = "Password123!";

    @Test(groups = "smoke", priority = 1)
    public void userCanRegister() {

        Response response = formRequest()
                .formParam("name", "Test User")
                .formParam("email", email)
                .formParam("password", password)
                .post("/users/register");

        if (response.statusCode() != 201) {
            response.then().log().all();
        }

        Assert.assertEquals(response.getStatusCode(), 201);
        response.then().log().all();
    }

    @Test(groups = "smoke", priority = 2)
    public void userCanLogin() {

        Response response = formRequest()
                .formParam("email", email)
                .formParam("password", password)
                .post("/users/login");

        response.then().log().all();

        token = response.jsonPath().getString("data.token");
        System.out.println("TOKEN = " + token);
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test(groups = "smoke", priority = 3)
    public void authorizedUserCanGetProfile() {
        Response response = requestWithAuth(token)
                .get("/users/profile");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("data.email"), email);
    }

    @Test(groups = "smoke", priority = 4)
    public void userCanLogout() {
        Response response = requestWithAuth(token)
                .delete("/users/logout");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}


