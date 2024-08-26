package config.api;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:api/api_credentials.properties"})
public interface ApiCredentials extends Config {
    String apiKey();
    String token();
}
