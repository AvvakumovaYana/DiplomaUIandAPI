package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:api_credentials.properties"})
public interface ApiCredentials extends Config {
    String apiKey();
    String token();
}
