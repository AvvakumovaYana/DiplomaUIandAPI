package helpers.mobile;

import config.mobile.DeviceConfig;
import org.aeonbits.owner.ConfigFactory;
import static io.restassured.RestAssured.given;

public class Browserstack {
    private static final DeviceConfig config = ConfigFactory.create(DeviceConfig.class, System.getProperties());

    public static String videoUrl(String sessionId) {
        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .auth().basic(config.username(), config.password())
                .get(url)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}