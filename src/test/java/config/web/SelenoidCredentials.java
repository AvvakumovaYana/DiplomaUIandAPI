package config.web;

import org.aeonbits.owner.Config;

@Config.Sources({"file:selenoid_credentials.properties"})
public interface SelenoidCredentials extends Config {
    String wdLogin();

    String wdPassword();
}
