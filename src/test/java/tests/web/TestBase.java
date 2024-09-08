package tests.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CommonConfig;
import config.web.SelenoidCredentials;
import helpers.api.trello.Boards;
import helpers.api.trello.Cards;
import helpers.api.trello.Lists;
import helpers.web.Attach;
import helpers.web.CookiesHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.web.BoardCreationForm;
import pages.web.LoginPage;
import pages.web.MainBoardsPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    private static final LoginPage loginPage = new LoginPage();
    private static final SelenoidCredentials selenoidCredentials = ConfigFactory.create(SelenoidCredentials.class, System.getProperties());
    private static final CommonConfig commonConfig = ConfigFactory.create(CommonConfig.class, System.getProperties());
    private static final CookiesHelper cookiesHelper = new CookiesHelper();
    protected final MainBoardsPage mainBoardsPage = new MainBoardsPage();
    protected final BoardCreationForm boardCreationForm = new BoardCreationForm();
    protected final Boards boardsApi = new Boards();
    protected final Lists listsApi = new Lists();
    protected final Cards cardsApi = new Cards();

    @BeforeAll
    static void beforeAll() throws Exception {
        Configuration.baseUrl = commonConfig.baseUri();
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;

        Configuration.remote = "https://" + selenoidCredentials.wdLogin() + ":" + selenoidCredentials.wdPassword() + "@" +
                System.getProperty("Wdhost", "selenoid.autotests.cloud/wd/hub");
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
            LoginPage.login();
            cookiesHelper.saveCookies();
        } catch (Exception e) {
            System.out.println("Ошибка получения куки через форму логина, используем существующий файл");
        }

        cookiesHelper.loadCookies();

        open("/");
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}