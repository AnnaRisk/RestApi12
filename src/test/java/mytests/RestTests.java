package mytests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RestTests {

    @Test
    void createTest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void updateTest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void listTest() {
        RestAssured
                .given().log().all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then().log().all()
                .statusCode(greaterThanOrEqualTo(200))
                .statusCode(lessThanOrEqualTo(299))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Content-Type", containsString("application/json"))
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2));
    }

    @Test
    void registerTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }
    @Test
    void listUserTest() {
        RestAssured
                .given().log().all()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .statusCode(greaterThanOrEqualTo(200))
                .statusCode(lessThanOrEqualTo(299))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Content-Type", containsString("application/json"))
              //  .body("id", is(2))
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2));

    }

}

