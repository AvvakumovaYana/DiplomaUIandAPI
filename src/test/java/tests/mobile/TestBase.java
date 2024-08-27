package tests.mobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.AppiumDriver;
import drivers.BrowserstackDriver;
import helpers.mobile.AttachMobile;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        if (System.getProperty("device") == null)
            System.setProperty("device", "browserstack");

        Configuration.browserSize = null;
        Configuration.browser = switch (System.getProperty("device")) {
            case "browserstack" -> BrowserstackDriver.class.getName();
            case "emulated", "real" -> AppiumDriver.class.getName();
            default -> throw new IllegalStateException("Unexpected value: " + System.getProperty("device"));
        };
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        String sessionId = Selenide.sessionId().toString();
        System.out.println(sessionId);

        AttachMobile.pageSource();
        closeWebDriver();

        if (Objects.equals(System.getProperty("device"), "browserstack"))
            AttachMobile.addVideo(sessionId);
    }
}
