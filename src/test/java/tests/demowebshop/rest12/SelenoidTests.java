package tests.demowebshop.rest12;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {
    /*
        1. make request to https://selenoid.autotests.cloud/status
        2. get response {"total":20,"used":0,"queued":0,"pending":0,"browsers":
            {"chrome":{"100.0":{},"99.0":{}},"firefox":{"97.0":{},"98.0":{}},"opera":{"84.0":{},"85.0":{}}}}
        3. check total is 20
     */

    @Test
    void checkTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkWithoutGivenTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkWithLogsTotal() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(20));
    }

    @Test
    void checkWithSomeLogsTotal() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .body("total", is(20));
    }


    @Test
    void checkChrome() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkTotalBadPractice() {
        Response response = get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().response();

        System.out.println(response.asString());

        String expectedResponse = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{" +
                "\"chrome\":{\"100.0\":{},\"99.0\":{}}," +
                "\"firefox\":{\"97.0\":{},\"98.0\":{}}," +
                "\"opera\":{\"84.0\":{},\"85.0\":{}}}}\n";

        assertEquals(expectedResponse, response.asString());
    }

    @Test
    void checkTotalGoodPractice() {
        Integer actualTotal = get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().path("total");

        System.out.println(actualTotal);

        Integer expectedTotal = 20;
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void checkWithStatusTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void check401StatusTotal() {
        get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(401);
    }

    @Test
    void check200StatusWithAuthInUrlTotal() {
        get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }


    @Test
    void check200StatusWithAuthTotal() {
        given()
                .auth().basic("user1", "1234")
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }


}