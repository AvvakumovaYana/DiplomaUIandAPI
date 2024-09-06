package tests.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import config.CommonConfig;
import config.web.WebCredentials;
import helpers.web.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    private static final WebCredentials webCredentials = ConfigFactory.create(WebCredentials.class, System.getProperties());
    private static final CommonConfig commonConfig = ConfigFactory.create(CommonConfig.class, System.getProperties());
    private static final String cookieFileName = "Cookies.data";

    @BeforeAll
    static void beforeAll() throws Exception {
        Configuration.baseUrl = commonConfig.baseUri();
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;

        //Configuration.remote = System.getProperty("Wdhost", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        Configuration.browser = System.getProperty("Browser", "chrome");
        if (Configuration.browser.equals("chrome")) {
            Configuration.browserVersion = System.getProperty("ChromeVersion", "126.0");
        } else if (Configuration.browser.equals("firefox")) {
            Configuration.browserVersion = System.getProperty("FirefoxVersion", "125.0");
        } else {
            throw new Exception("Неверный браузер! " + Configuration.browser);
        }
        Configuration.browserSize = System.getProperty("BrowserSize", "1920x1080");

        SelenideLogger.addListener("allure", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        try {
            createCookies();
        } catch (Exception e) {
            System.out.println("Ошибка получения куки через форму логина, используем существующий файл");
        }

        setCookies();

        open("/");
    }

    private static void createCookies() throws InterruptedException, IOException {
        open("/");

        $(byText("Log in")).click();
        $("#username").setValue(webCredentials.login());
        $("#login-submit").click();
        ensureElement("#password", 10000);
        $("#password").setValue(webCredentials.password());
        $("#login-submit").click();
        Thread.sleep(1000);

        open("/");

        var file = new File(cookieFileName);

        file.delete();
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        CSVWriter csv = new CSVWriter(fw);
        var cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        for (Cookie cookie : cookies) {
            var expiry = cookie.getExpiry();

            var line = new String[6];
            line[0] = cookie.getName();
            line[1] = cookie.getValue();
            line[2] = cookie.getDomain();
            line[3] = cookie.getPath();
            line[4] = (expiry != null ? String.valueOf(expiry.getTime()) : "null");
            line[5] = String.valueOf(cookie.isSecure());
            csv.writeNext(line);
        }
        csv.close();
        fw.close();
    }

    private static void setCookies() throws Exception {
        File file = new File(cookieFileName);
        if (!file.exists())
            throw new Exception("Файл с куки не найден");

        FileReader fileReader = new FileReader(file);
        CSVReader csv = new CSVReader(fileReader);
        List<String[]> data = csv.readAll();
        for (var line : data) {
            String name = line[0];
            String value = line[1];
            String domain = line[2];
            String path = line[3];
            String expiryString = line[4];
            boolean isSecure = Boolean.parseBoolean(line[5]);

            Date expiry = null;
            if (!expiryString.equals("null")) {
                expiry = new Date(Long.parseLong(expiryString));
            }
            Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
            System.out.println(ck);
            WebDriverRunner.getWebDriver().manage().addCookie(ck);
        }
    }

    private static void ensureElement(String elementSelector, int waitingPeriodInMilliseconds) throws InterruptedException {
        while (!$(elementSelector).isDisplayed() && waitingPeriodInMilliseconds > 0) {
            Thread.sleep(100);
            waitingPeriodInMilliseconds -= 100;
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