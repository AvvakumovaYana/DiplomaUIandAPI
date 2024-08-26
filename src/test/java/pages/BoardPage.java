package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import helpers.api.TrelloAPI;
import lombok.Getter;
import models.BoardModel;

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
    private final SelenideElement closeBoardFormLink = $("[href='/u/user17009278/boards']");
    private final SelenideElement formCloseButton = $("[data-testid='popover-close']");
    private final SelenideElement closeBoardButton = $("[data-testid='close-board-confirm-button']");
    private final SelenideElement deleteBoard = $("[data-testid='close-board-delete-board-button']");
    private final SelenideElement deleteBoardFormTitle = $(".TzntopStGOcVjM");
    private final SelenideElement deleteBoardFormText = $(".q2PzD_Dkq1FVX3");
    private final SelenideElement deleteBoardButton = $("[data-testid='close-board-delete-board-confirm-button']");

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

    public BoardPage openPage(BoardModel model) {
        open(model.getUrl());
        boardTitle.shouldBe(visible).shouldHave(text(model.getName()));
        return this;
    }

    public BoardPage changeBoardTitle (String name) {
        this.name = name;
        boardTitle.click();
        boardTitleField.sendKeys(name);
        boardTitleField.pressEnter();
        return this;
    }

    public BoardPage openBoardMenu () {
        boardMenu.click();
        return this;
    }

    public BoardPage clickCloseBoard () {
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

    public BoardPage clickCloseBoardButton () {
        closeBoardButton.shouldBe(visible).shouldHave(value("Закрыть"));
        closeBoardButton.click();
        return this;
    }

    public BoardPage clickDeleteBoard () {
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

    public BoardPage clickDeleteBoardButton () {
        deleteBoardButton.shouldBe(visible).shouldHave(text("Удалить"));
        deleteBoardButton.click();
        return this;
    }

    public String getBoardId() throws Exception {
        return TrelloAPI.getBoardId(WebDriverRunner.getWebDriver().getCurrentUrl());
    }
}
