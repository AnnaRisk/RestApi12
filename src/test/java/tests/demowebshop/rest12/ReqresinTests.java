package tests.demowebshop.rest12;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {

    @BeforeAll
    static void testBase() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void createTest() {
        String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void deleteTest() {
        delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void registerUnsuccessfulTest() {
        String body = "{\"email\": \"my@email.com\"}";
        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void loginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void listUsersTest() {
        get("/users")
                .then()
                .log().body()
                .body("page",is(1))
                .body("per_page", is(6))
                .body("total",is(12))
                .body("total_pages",is(2));
    }
}
