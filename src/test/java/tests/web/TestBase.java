package tests.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class TestBase {
    @BeforeAll
    static void beforeAll() throws Exception {
        Configuration.baseUrl = "https://trello.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;

        Configuration.holdBrowserOpen = true;

        Configuration.remote = System.getProperty("Wdhost","https://user1:1234@selenoid.autotests.cloud/wd/hub");
        Configuration.browser = System.getProperty("Browser","chrome");
        if (Configuration.browser.equals("chrome")) {
            Configuration.browserVersion = System.getProperty("ChromeVersion", "126.0");
        }
        else if (Configuration.browser.equals("firefox")) {
            Configuration.browserVersion = System.getProperty("FirefoxVersion","125.0");
        }
        else {
            throw new Exception("Неверный браузер! " + Configuration.browser);
        }
        Configuration.browserSize = System.getProperty("BrowserSize","1920x1080");

        SelenideLogger.addListener("allure", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        open("/");

        File file = new File("Cookies.data");
        FileReader fileReader = new FileReader(file);
        BufferedReader Buffreader = new BufferedReader(fileReader);
        String strline;
        while ((strline = Buffreader.readLine()) != null) {
            StringTokenizer token = new StringTokenizer(strline, ";");
            while (token.hasMoreTokens()) {
                String name = token.nextToken();
                String value = token.nextToken();
                String domain = token.nextToken();
                String path = token.nextToken();
                Date expiry = null;

                String val;
                if (!(val = token.nextToken()).equals("null")) {
                    expiry = new Date(Long.parseLong(val));
                }
                boolean isSecure = Boolean.parseBoolean(token.nextToken());
                Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                System.out.println(ck);
                WebDriverRunner.getWebDriver().manage().addCookie(ck); // This will add the stored cookie to your current session
            }
        }
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}