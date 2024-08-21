package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainBoardsPage {
    private final SelenideElement pageTitle = $(".boards-page-section-header-name");
    private final SelenideElement createBoardButton = $(".mod-add");

    private final String pageTitleValue = "ВАШИ РАБОЧИЕ ПРОСТРАНСТВА";

    public MainBoardsPage openPage() {
        open("/");
        pageTitle.shouldBe(visible).shouldHave(text(pageTitleValue));
        return this;
    }

    public MainBoardsPage clickCreateBoardButton() {
        createBoardButton.shouldBe(visible).shouldHave(text("Создать доску"));
        createBoardButton.click();
        return this;
    }

    public MainBoardsPage checkPageTitle() {
        pageTitle.shouldBe(visible).shouldHave(text(pageTitleValue));
        return this;
    }
}
