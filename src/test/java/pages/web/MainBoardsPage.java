package pages.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainBoardsPage {
    private final SelenideElement pageTitle = $(".boards-page-section-header-name");
    private final SelenideElement createBoardButton = $(".mod-add");

    private final String pageTitleValue = "ВАШИ РАБОЧИЕ ПРОСТРАНСТВА";

    public void openPage() {
        open("/");
        Selenide.refresh();
        pageTitle.shouldBe(visible).shouldHave(text(pageTitleValue));
    }

    public void checkCreateBoardButtonCaption(String caption) {
        createBoardButton.shouldBe(visible).shouldHave(text(caption));
    }

    public void clickCreateBoardButton() {
        createBoardButton.click();
    }

    public void checkPageTitle() {
        pageTitle.shouldBe(visible).shouldHave(text(pageTitleValue));
    }
}
