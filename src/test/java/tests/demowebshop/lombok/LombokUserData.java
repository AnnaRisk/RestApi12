package tests.demowebshop.lombok;


import org.junit.jupiter.api.Test;
import tests.demowebshop.model.UserResponse;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.demowebshop.lombok.User.*;

public class LombokUserData {

    String
            body = "{\"name\": \"morpheus\",\"job\": \"leader\" }",
            body2 = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}",
            body3 = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }",
            name = "morpheus",

    token = "QpwL5tke4Pnpja7X4",
            job = "leader";

Integer id =4;

    @Test
    void listUsersTest() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total", is(12));

    }

    @Test
    void createUser() {
        // String body = "{\"name\": \"morpheus\", \"job\": \"leader\" }";
        UserResponse data = given()
                .spec(request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(response201)
                .extract().as(UserResponse.class);
        assertEquals(name, data.getName());
        assertEquals(job, data.getJob());
    }

    @Test
    void deleteUser() {
        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then()
                .spec(response204);
    }


    @Test
    void registerSuccesfull() {
      //  String body = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        UserResponse data = given()
                .spec(request)
                .body(body3)
                .when()
                .post("/register")
                .then()
                .log().all()
                .spec(responseSpec)
                .extract().as(UserResponse.class);
        assertEquals(id, data.getId());
        assertEquals(token, data.getToken());
    }
    @Test
    public void checkEmailGroovy() {

        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("tracey.ramos@reqres.in"));

    }
}
