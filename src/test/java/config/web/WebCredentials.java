package config.web;

import org.aeonbits.owner.Config;

@Config.Sources({"file:web_credentials.properties"})
public interface WebCredentials extends Config {
    String login();

    String password();
}
