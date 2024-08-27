package pages.mobile;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;
import static org.openqa.selenium.By.xpath;

public class AuthorizationPage {
    private final SelenideElement logInButton = $(xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[3]/android.widget.Button"));
    private final SelenideElement singInWithEmailButton = $(id("com.trello:id/email"));
    private final SelenideElement enterYourEmailField = $(xpath("//android.widget.EditText[@resource-id=\"username\"]"));
    private final SelenideElement logInToContinueLabel = $(xpath("//*[@text=\"Log in to continue\"]"));
    private final SelenideElement continueButton = $(xpath("//android.widget.Button[@resource-id=\"login-submit\"]"));
    private final SelenideElement enterPasswordButton = $(xpath("//android.widget.EditText[@resource-id=\"password\"]"));
    private final SelenideElement nextLogInButton = $(xpath("//android.widget.Button[@resource-id=\"login-submit\"]"));
    private final SelenideElement pageLabel = $(xpath("//android.widget.TextView[@text=\"Boards\"]"));

    public AuthorizationPage clickLogInButton() {
        logInButton.click();
        return this;
    }

    public AuthorizationPage clickSingInWithEmailButton() {
        singInWithEmailButton.click();
        return this;
    }

    public AuthorizationPage fillEnterYourEmailField() {
        enterYourEmailField.click();
        enterYourEmailField.sendKeys("yana.for.test@gmail.com");
        return this;
    }

    public AuthorizationPage clickLogInToContinueLabel() {
        logInToContinueLabel.click();
        return this;
    }

    public AuthorizationPage clickContinueButton() {
        continueButton.click();
        return this;
    }

    public AuthorizationPage fillEnterPasswordButton() {
        enterPasswordButton.sendKeys("yanafortest");
        return this;
    }

    public AuthorizationPage clickNextLogInButton() {
        nextLogInButton.click();
        return this;
    }

    public AuthorizationPage checkPageLabel() {
        pageLabel.shouldHave(text("Boards"));
        return this;
    }
}
