package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BoardCreationForm {
    private final SelenideElement boardNameField = $("[data-testid='create-board-title-input']");
    private final SelenideElement createButton = $("[data-testid='create-board-submit-button']");

    public BoardCreationForm fillBoardNameField(String name) {
        boardNameField.setValue(name);
        return this;
    }

    public void clickCreateButton() {
        createButton.click();
    }
}
