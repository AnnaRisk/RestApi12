package tests.demowebshop.lombok;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class User {

    public static RequestSpecification request = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(ContentType.JSON);


    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification response204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification response201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

}
