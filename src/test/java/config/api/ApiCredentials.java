package config.api;

import org.aeonbits.owner.Config;

@Config.Sources({"file:api_credentials.properties"})
public interface ApiCredentials extends Config {
    String apiKey();

    String token();
}
