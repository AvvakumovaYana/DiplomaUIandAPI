package specs;

import config.ApiCredentials;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static helpers.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ResponseSpec {
    private static final ApiCredentials apiCredentials = ConfigFactory.create(ApiCredentials.class);

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplate())
            .baseUri("https://api.trello.com/1")
            .header(new Header("Authorization", "OAuth oauth_consumer_key=\"" + apiCredentials.apiKey() + "\", oauth_token=\"" + apiCredentials.token() + "\""))
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .build();
}
