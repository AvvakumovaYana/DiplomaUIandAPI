package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.api.AuthorizationHelper.getAuthorizationHeader;
import static helpers.api.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
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
