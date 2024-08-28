package pages.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainBoardsPage {
    private final SelenideElement pageTitle = $(".boards-page-section-header-name");
    private final SelenideElement createBoardButton = $(".mod-add");

    private final String pageTitleValue = "ВАШИ РАБОЧИЕ ПРОСТРАНСТВА";

    public MainBoardsPage openPage() throws InterruptedException {
        open("/");
        Selenide.refresh();
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
