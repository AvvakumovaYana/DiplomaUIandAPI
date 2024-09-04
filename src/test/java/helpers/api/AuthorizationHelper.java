package helpers.api;

import config.api.ApiCredentials;
import io.restassured.http.Header;
import org.aeonbits.owner.ConfigFactory;

public class AuthorizationHelper {
    private static final ApiCredentials apiCredentials = ConfigFactory.create(ApiCredentials.class, System.getProperties());

    public static Header getAuthorizationHeader() {
        return new Header("Authorization",
                "OAuth oauth_consumer_key=\"" + apiCredentials.apiKey() +
                        "\", oauth_token=\"" + apiCredentials.token() + "\"");
    }
}
