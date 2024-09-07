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
    private final SelenideElement closeBoard = $(".js-close-board");
    private final SelenideElement closeBoardFormTitle = $(".pop-over-header-title");
    private final SelenideElement closeBoardFormText = $(".pop-over-content");
    private final SelenideElement closeBoardFormLink = $("[href^='/u/user'][href$='/boards']");
    private final SelenideElement formCloseButton = $("[data-testid='popover-close']");
    private final SelenideElement closeBoardButton = $("[data-testid='close-board-confirm-button']");
    private final SelenideElement deleteBoard = $("[data-testid='close-board-delete-board-button']");
    private final SelenideElement deleteBoardFormTitle = $(".TzntopStGOcVjM");
    private final SelenideElement deleteBoardFormText = $(".q2PzD_Dkq1FVX3");
    private final SelenideElement deleteBoardButton = $("[data-testid='close-board-delete-board-confirm-button']");
    private final SelenideElement createCardButton = $("[data-testid='list-add-card-button']");
    private final SelenideElement cardNameField = $("[data-testid='list-card-composer-textarea']");
    private final SelenideElement addCardButton = $("[data-testid='list-card-composer-add-card-button']");
    private final SelenideElement cardLabel = $("[data-testid='card-name']");

    @Getter
    private String name;

    public BoardPage(String name) {
        this.name = name;
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

    public BoardPage clickCloseBoard() {
        closeBoard.scrollTo()
                .shouldBe(visible)
                .shouldHave(text(" Закрыть доску"))
                .click();
        return this;
    }

    public BoardPage checkCloseBoardFormTitle() {
        closeBoardFormTitle.shouldBe(visible).shouldHave(text("Закрытие доски"));
        return this;
    }

    public BoardPage checkCloseBoardFormText() {
        closeBoardFormText.shouldBe(visible)
                .shouldHave(text("Закрытые доски можно найти и повторно открыть в нижней части "));
        return this;
    }

    public BoardPage checkCloseBoardFormLink() {
        closeBoardFormLink.shouldBe(visible).shouldHave(text("страницы досок"));
        return this;
    }

    public BoardPage checkFormCloseButton() {
        formCloseButton.shouldBe(visible);
        return this;
    }

    public BoardPage clickCloseBoardButton() {
        closeBoardButton.shouldBe(visible).shouldHave(value("Закрыть"));
        closeBoardButton.click();
        return this;
    }

    public BoardPage clickDeleteBoard() {
        deleteBoard.shouldBe(visible).shouldHave(text("Удалить доску навсегда"));
        deleteBoard.click();
        return this;
    }

    public BoardPage checkDeleteBoardFormTitle() {
        deleteBoardFormTitle.shouldBe(visible).shouldHave(text("Удалить доску?"));
        return this;
    }

    public BoardPage checkDeleteBoardFormText() {
        deleteBoardFormText.shouldBe(visible)
                .shouldHave(text("Все списки, карточки и действия будут удалены, и вы не сможете повторно открыть доску. " +
                        "Отмена невозможна."));
        return this;
    }

    public void clickDeleteBoardButton() {
        deleteBoardButton.shouldBe(visible).shouldHave(text("Удалить"));
        deleteBoardButton.click();
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
