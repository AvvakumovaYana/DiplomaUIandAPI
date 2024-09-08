package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainBoardsPage {
    private final SelenideElement createBoardButton = $(".mod-add");

    private final String pageTitleValue = "ВАШИ РАБОЧИЕ ПРОСТРАНСТВА";

    public void openPage() {
        open("/");
    }

    public void clickCreateBoardButton() {
        createBoardButton.click();
    }
}
