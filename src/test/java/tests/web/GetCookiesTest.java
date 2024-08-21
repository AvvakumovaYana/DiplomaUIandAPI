package tests.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class GetCookiesTest {
    @Test
    void RefreshCookies() throws InterruptedException, IOException {
        Configuration.baseUrl = "https://trello.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;
        Configuration.holdBrowserOpen = true;

        open("/");

        $(byText("Log in")).click();
        $("#username").setValue("yana.for.test@gmail.com");
        $("#login-submit").click();
        Thread.sleep(10000);
        $("#password").setValue("yanafortest");
        $("#login-submit").click();
        Thread.sleep(1000);

        open("/");

        var file = new File("Cookies.data");
        file.delete();
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        var cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        for (Cookie cookie : cookies) {
            var expiry = cookie.getExpiry();
            var line = cookie.getName() + ";" +
                    cookie.getValue() + ";" +
                    cookie.getDomain() + ";" +
                    cookie.getPath() + ";" +
                    (expiry != null ? expiry.getTime() : "null") + ";" +
                    cookie.isSecure();

            bw.write(line);
            bw.newLine();
        }
        bw.close();
        fw.close();
    }
}
