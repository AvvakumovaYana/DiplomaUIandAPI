package pages.web;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import models.BoardModel;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BoardPage {
    private final SelenideElement boardTitle = $("[data-testid='board-name-display']");
    private final SelenideElement boardTitleField = $("[data-testid='board-name-input']");
    private final SelenideElement boardMenuButton = $("[aria-label='Меню']");
    private final SelenideElement createCardButton = $("[data-testid='list-add-card-button']");
    private final SelenideElement cardNameField = $("[data-testid='list-card-composer-textarea']");
    private final SelenideElement saveCardButton = $("[data-testid='list-card-composer-add-card-button']");
    private final SelenideElement cardLabel = $("[data-testid='card-name']");
    private final SelenideElement addListButton = $("[data-testid='list-composer-button']");
    private final SelenideElement listNameField = $("[data-testid='list-name-textarea']");
    private final SelenideElement saveListButton = $("[data-testid='list-composer-add-list-button']");

    @Getter
    private final BoardMenu menu;
    @Getter
    private String name;

    public BoardPage(String name) {
        this.name = name;
        this.menu = new BoardMenu();
    }

    public void openPage(BoardModel model) {
        open(model.getUrl());
    }

    public void changeBoardTitle(String name) {
        this.name = name;
        boardTitle.click();
        boardTitleField.sendKeys(name);
        boardTitleField.pressEnter();
    }

    public BoardPage openBoardMenu() {
        boardMenuButton.click();
        return this;
    }

    public void clickCreateCardButton() {
        createCardButton.click();
    }

    public void fillCardNameField(String value) {
        cardNameField.setValue(value);
    }

    public void clickSaveCardButton() {
        saveCardButton.click();
    }

    public void clickCardLabel() {
        cardLabel.click();
    }

    public BoardPage clickAddListButton() {
        addListButton.click();
        return this;
    }

    public void fillListNameField(String value) {
        listNameField.setValue(value);
    }

    public void clickSaveListButton() {
        saveListButton.click();
    }

    public String getBoardId() throws Exception {
        String boardUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        var parts = boardUrl.split(Pattern.quote("/"));

        if (parts.length != 6)
            throw new Exception(String.format("Wrong format of url \"%s\" count of parts is %s", boardUrl, parts.length));
        return parts[4];
    }
}
