package api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    public BaseApi() {
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
    }

    protected RequestSpecification request() {
        return RestAssured.given();
    }

    protected RequestSpecification formRequest() {
        return RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8");
    }


    protected RequestSpecification requestWithAuth(String token) {
        return request()
                .header("x-auth-token", token);
    }


    protected Response get(String endpoint) {
        return request().get(endpoint);
    }


    protected Response post(String endpoint, Object body) {
        return request().body(body).post(endpoint);
    }


    protected Response getAuth(String endpoint, String token) {
        return requestWithAuth(token).get(endpoint);
    }


    protected Response postAuth(String endpoint, Object body, String token) {
        return requestWithAuth(token).body(body).post(endpoint);
    }
}