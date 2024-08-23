package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.AuthorizationHelper.getAuthorizationHeader;
import static helpers.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ResponseSpec {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplate())
            .baseUri("https://api.trello.com/1")
            .header(getAuthorizationHeader())
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .build();
}
