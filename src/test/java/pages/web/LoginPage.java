package pages.web;

import com.codeborne.selenide.SelenideElement;
import config.web.WebCredentials;
import org.aeonbits.owner.ConfigFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private static final SelenideElement loginButton = $(byText("Log in"));
    private static final SelenideElement usernameField = $("#username");
    private static final SelenideElement passwordField = $("#password");
    private static final SelenideElement loginSubmitButton = $("#login-submit");

    private static final WebCredentials webCredentials = ConfigFactory.create(WebCredentials.class, System.getProperties());

    public static void login() {
        open("/");

        loginButton.click();
        usernameField.setValue(webCredentials.login());
        loginSubmitButton.click();
        passwordField.shouldBe(visible, Duration.ofSeconds(10));
        passwordField.setValue(webCredentials.password());
        loginSubmitButton.click();

        sleep(1000);
    }
}
