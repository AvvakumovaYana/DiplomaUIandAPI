package pages.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import models.BoardModel;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BoardPage {
    private final SelenideElement boardTitle = $("[data-testid='board-name-display']");
    private final SelenideElement boardTitleField = $("[data-testid='board-name-input']");
    private final SelenideElement firstColumnTitle = $(byText("Нужно сделать"));
    private final SelenideElement secondColumnTitle = $(byText("В процессе"));
    private final SelenideElement thirdColumnTitle = $(byText("Готово"));
    private final SelenideElement boardMenu = $("[aria-label='Меню']");
    private final SelenideElement createCardButton = $("[data-testid='list-add-card-button']");
    private final SelenideElement cardNameField = $("[data-testid='list-card-composer-textarea']");
    private final SelenideElement addCardButton = $("[data-testid='list-card-composer-add-card-button']");
    private final SelenideElement cardLabel = $("[data-testid='card-name']");

    @Getter
    private String name;

    @Getter
    private final BoardMenu menu;

    public BoardPage(String name) {
        this.name = name;
        this.menu = new BoardMenu();
    }

    public BoardPage checkBoardTitle() {
        boardTitle.shouldBe(visible).shouldHave(text(name));
        return this;
    }

    public BoardPage checkFirstColumnTitle() {
        firstColumnTitle.shouldBe(visible);
        return this;
    }

    public BoardPage checkSecondColumnTitle() {
        secondColumnTitle.shouldBe(visible);
        return this;
    }

    public BoardPage checkThirdColumnTitle() {
        thirdColumnTitle.shouldBe(visible);
        return this;
    }

    public void openPage(BoardModel model) {
        open(model.getUrl());
        Selenide.refresh();
        boardTitle.shouldBe(visible).shouldHave(text(model.getName()));
    }

    public void changeBoardTitle(String name) {
        this.name = name;
        boardTitle.click();
        boardTitleField.sendKeys(name);
        boardTitleField.pressEnter();
    }

    public BoardPage openBoardMenu() {
        Selenide.refresh();
        boardMenu.click();
        return this;
    }

    public void clickCreateCardButton() {
        createCardButton.click();
    }

    public void fillCardNameField(String value) {
        cardNameField.setValue(value);
    }

    public void clickAddCardButton() {
        addCardButton.click();
    }

    public void clickCardLabel() {

        cardLabel.click();
    }

    public void checkCardLabel(String cardName) {
        cardLabel.shouldHave(text(cardName));
    }

    public void checkEmptyCard() {
        cardLabel.shouldNot(exist);
    }

    public String getBoardId() throws Exception {
        String boardUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        var parts = boardUrl.split(Pattern.quote("/"));

        if (parts.length != 6)
            throw new Exception(String.format("Wrong format of url \"%s\" count of parts is %s", boardUrl, parts.length));
        return parts[4];
    }
}
