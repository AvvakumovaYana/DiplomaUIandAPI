package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BoardMenu {
    private final SelenideElement closeBoard = $(".js-close-board");
    private final SelenideElement closeBoardButton = $("[data-testid='close-board-confirm-button']");
    private final SelenideElement deleteBoard = $("[data-testid='close-board-delete-board-button']");
    private final SelenideElement deleteBoardButton = $("[data-testid='close-board-delete-board-confirm-button']");

    public BoardMenu clickCloseBoard() {
        closeBoard.click();
        return this;
    }

    public BoardMenu clickCloseBoardButton() {
        closeBoardButton.click();
        return this;
    }

    public BoardMenu clickDeleteBoard() {
        deleteBoard.click();
        return this;
    }

    public void clickDeleteBoardButton() {
        deleteBoardButton.click();
    }
}
