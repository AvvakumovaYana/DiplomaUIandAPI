package tests.mobile;

import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.mobile.AuthorizationPage;

import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

@Owner("Аввакумова Яна")
@Tag("mobile")

public class AuthorizationTest extends TestBase {
    private AuthorizationPage authorizationPage = new AuthorizationPage();

    @Test
    @AllureId("34130")
    @DisplayName("Проверка авторизации в приложении Trello")
    void loginTest() throws InterruptedException {
        step("Нажимаем на кнопку 'Log in'", () -> {
            authorizationPage.clickLogInButton();
        });
        step("Нажимаем на кнопку 'SIGN IN WITH EMAIL'", () -> {
            authorizationPage.clickSingInWithEmailButton();
        });

        Thread.sleep(15000);

        step("Нажимаем на кнопку 'Add another account', если она есть", () -> {
            var list = $$(xpath("//android.widget.Button[@resource-id=\"navigate-to-login-prompt\"]"));
            if (list.isEmpty() == false)
                list.first().click();
        });
        step("Заполняем поле 'Enter your email'", () -> {
            authorizationPage.fillEnterYourEmailField();
        });
        step("Нажимаем на текст страницы, чтобы убрать подсказку для ввода", () -> {
            authorizationPage.clickLogInToContinueLabel();
        });
        step("Нажимаем на кнопку 'Continue'", () -> {
            authorizationPage.clickContinueButton();
        });

        Thread.sleep(5000);

        step("Заполняем поле 'Enter password'", () -> {
            authorizationPage.fillEnterPasswordButton();
        });
        step("Нажимаем на кнопку 'Log in'", () -> {
            authorizationPage.clickNextLogInButton();
        });

        Thread.sleep(20000);

        step("Проверяем заголовок экрана Boards", () -> {
            authorizationPage.checkPageLabel();
        });
    }
}


