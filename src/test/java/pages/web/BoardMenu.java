package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class BoardMenu {
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

    public BoardMenu checkCloseBoardCaption(String caption) {
        closeBoard.scrollTo()
                .shouldBe(visible)
                .shouldHave(text(caption));
        return this;
    }

    public BoardMenu clickCloseBoard() {
        closeBoard.click();
        return this;
    }

    public BoardMenu checkCloseBoardFormTitle(String title) {
        closeBoardFormTitle.shouldBe(visible).shouldHave(text(title));
        return this;
    }

    public BoardMenu checkCloseBoardFormText() {
        closeBoardFormText.shouldBe(visible)
                .shouldHave(text("Закрытые доски можно найти и повторно открыть в нижней части "));
        return this;
    }

    public BoardMenu checkCloseBoardFormLink(String text) {
        closeBoardFormLink.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public BoardMenu checkFormCloseButton() {
        formCloseButton.shouldBe(visible);
        return this;
    }

    public BoardMenu checkCloseBoardButtonCaption(String caption) {
        closeBoardButton.shouldBe(visible).shouldHave(value(caption));
        return this;
    }

    public BoardMenu clickCloseBoardButton() {
        closeBoardButton.click();
        return this;
    }

    public BoardMenu checkDeleteBoardCaption(String caption) {
        deleteBoard.shouldBe(visible).shouldHave(text(caption));
        return this;
    }

    public BoardMenu clickDeleteBoard() {
        deleteBoard.click();
        return this;
    }

    public BoardMenu checkDeleteBoardFormTitle(String title) {
        deleteBoardFormTitle.shouldBe(visible).shouldHave(text(title));
        return this;
    }

    public BoardMenu checkDeleteBoardFormText() {
        deleteBoardFormText.shouldBe(visible)
                .shouldHave(text("Все списки, карточки и действия будут удалены, и вы не сможете повторно открыть доску. " +
                        "Отмена невозможна."));
        return this;
    }

    public BoardMenu checkDeleteBoardButtonCaption(String caption) {
        deleteBoardButton.shouldBe(visible).shouldHave(text(caption));
        return this;
    }

    public void clickDeleteBoardButton() {
        deleteBoardButton.click();
    }
}
