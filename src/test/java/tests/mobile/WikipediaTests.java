package tests.mobile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selenide.$;

public class WikipediaTests extends TestBase {

    @Test
    @DisplayName("Проверка стартовой страницы википедии")
    void loginTest() throws InterruptedException {
        step("Нажимаем на кнопку 'Log in'", () -> {
//            $("[text='Log in']").click();
            $(xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[3]/android.widget.Button")).click();
        });
        step("Нажимаем на кнопку 'SIGN IN WITH EMAIL'", () -> {
            $(id("com.trello:id/email")).click();
        });

        Thread.sleep(15000);

        step("Нажимаем на кнопку 'Add another account' если она есть", () -> {
            var list = $$(xpath("//android.widget.Button[@resource-id=\"navigate-to-login-prompt\"]"));
            if (list.isEmpty() == false)
                list.first().click();
        });
        step("Заполняем поле 'Enter your email'", () -> {
            $(xpath("//android.widget.EditText[@resource-id=\"username\"]")).click();
            $(xpath("//android.widget.EditText[@resource-id=\"username\"]")).sendKeys("yana.for.test@gmail.com");
        });
        step("Нажимаем на страницу, чтобы убрать подсказку для ввода", () -> {
            //$(xpath("//android.widget.TextView[@text=\"Log in to continue\"]")).click();
            $(xpath("//*[@text=\"Log in to continue\"]")).click();
        });
        step("Нажимаем на кнопку 'Continue'", () -> {
            $(xpath("//android.widget.Button[@resource-id=\"login-submit\"]")).click();
        });

        Thread.sleep(5000);

        step("Заполняем поле 'Enter password'", () -> {
            $(xpath("//android.widget.EditText[@resource-id=\"password\"]")).sendKeys("yanafortest");
        });
        step("Нажимаем на кнопку 'Log in'", () -> {
            $(xpath("//android.widget.Button[@resource-id=\"login-submit\"]")).click();
        });

        Thread.sleep(20000);

        step("Проверяем заголовок экрана Boards", () -> {
            $(xpath("//android.widget.TextView[@text=\"Boards\"]"))
                    .shouldHave(text("Boards"));
        });
    }
}


