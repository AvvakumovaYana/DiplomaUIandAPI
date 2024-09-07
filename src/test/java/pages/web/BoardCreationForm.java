package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class BoardCreationForm {
    private final SelenideElement formTitle = $(".TzntopStGOcVjM");
    private final SelenideElement boardPreview = $(".VUpJ_1spSW_Fh7");
    private final SelenideElement labelBackground = $("[for='background-picker']");
    private final SelenideElement backgroundTypes = $("#background-picker");
    private final SelenideElement boardNameLabel = $(".fMvxkh4DHKJGnq");
    private final SelenideElement boardNameField = $("[data-testid='create-board-title-input']");
    private final SelenideElement setBoardNameLabel = $(".lWu5grh2rIDIym");
    private final SelenideElement boardVisibilityLabel = $(byText("Видимость"));
    private final SelenideElement boardVisibilityField = $(".css-4kbx6o-singleValue");
    private final SelenideElement createButton = $("[data-testid='create-board-submit-button']");
    private final SelenideElement templateButton = $("[data-testid='create-from-template-button']");
    private final SelenideElement agreementText = $(".ml7HZrT9aR6kxG");
    private final SelenideElement useTermsLink = $("[href='https://unsplash.com/terms']");
    private final SelenideElement licensesRulesLink = $("[href='https://unsplash.com/license']");
    private final SelenideElement closeButton = $("[data-testid='popover-close']");

    public BoardCreationForm checkFormTitle(String title) {
        formTitle.shouldBe(visible).shouldHave(text(title));
        return this;
    }

    public BoardCreationForm checkBoardPreview() {
        boardPreview.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkLabelBackground(String text) {
        labelBackground.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public BoardCreationForm checkBackgroundTypes() {
        backgroundTypes.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkBoardNameLabel(String text) {
        boardNameLabel.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public BoardCreationForm checkBoardNameField() {
        boardNameField.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkSetBoardNameLabel(String text) {
        setBoardNameLabel.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public BoardCreationForm checkBoardVisibilityLabel() {
        boardVisibilityLabel.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkBoardVisibilityField(String caption) {
        boardVisibilityField.shouldBe(visible).shouldHave(text(caption));
        return this;
    }

    public BoardCreationForm checkCreateButton(String caption) {
        createButton.shouldBe(visible)
                .shouldHave(attribute("disabled"))
                .shouldHave(text(caption));
        return this;
    }

    public BoardCreationForm checkTemplateButton(String caption) {
        templateButton.shouldBe(visible)
                .shouldBe(visible)
                .shouldHave(text(caption));
        return this;
    }

    public BoardCreationForm checkAgreementText(String text) {
        agreementText.shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    public BoardCreationForm checkUseTermsLink(String text) {
        useTermsLink.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public BoardCreationForm checkLicensesRulesLink(String text) {
        licensesRulesLink.shouldBe(visible).shouldHave(text(text));
        return this;
    }

    public void checkCloseButton() {
        closeButton.shouldBe(visible);
    }

    public BoardCreationForm fillBoardNameField(String name) {
        boardNameField.setValue(name);
        return this;
    }

    public void clickCreateButton() {
        createButton.click();
    }
}



