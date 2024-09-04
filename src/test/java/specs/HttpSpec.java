package specs;

import config.CommonConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static helpers.api.AuthorizationHelper.getAuthorizationHeader;
import static helpers.api.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class HttpSpec {
    private static final CommonConfig commonConfig = ConfigFactory.create(CommonConfig.class, System.getProperties());

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplate())
            .baseUri(commonConfig.apiUri())
            .header(getAuthorizationHeader())
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .build();
}
