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
    private final SelenideElement boardVisibilityField = $(".css-spyyn7-singleValue");
    private final SelenideElement createButton = $("[data-testid='create-board-submit-button']");
    private final SelenideElement templateButton = $("[data-testid='create-from-template-button']");
    private final SelenideElement agreementText = $(".ml7HZrT9aR6kxG");
    private final SelenideElement useTermsLink = $("[href='https://unsplash.com/terms']");
    private final SelenideElement licensesRulesLink = $("[href='https://unsplash.com/license']");
    private final SelenideElement closeButton = $("[data-testid='popover-close']");

    public BoardCreationForm checkFormTitle() {
        formTitle.shouldBe(visible).shouldHave(text("Создать доску"));
        return this;
    }

    public BoardCreationForm checkBoardPreview() {
        boardPreview.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkLabelBackground() {
        labelBackground.shouldBe(visible).shouldHave(text("Фон"));
        return this;
    }

    public BoardCreationForm checkBackgroundTypes() {
        backgroundTypes.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkBoardNameLabel() {
        boardNameLabel.shouldBe(visible).shouldHave(text("Заголовок доски"));
        return this;
    }

    public BoardCreationForm checkBoardNameField() {
        boardNameField.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkSetBoardNameLabel() {
        setBoardNameLabel.shouldBe(visible).shouldHave(text("Укажите название доски."));
        return this;
    }

    public BoardCreationForm checkBoardVisibilityLabel() {
        boardVisibilityLabel.shouldBe(visible);
        return this;
    }

    public BoardCreationForm checkBoardVisibilityField() {
        boardVisibilityField.shouldBe(visible).shouldHave(text("Рабочее пространство"));
        return this;
    }

    public BoardCreationForm checkCreateButton() {
        createButton.shouldBe(visible)
                .shouldHave(attribute("disabled"))
                .shouldHave(text("Создать"));
        return this;
    }
    public BoardCreationForm checkTemplateButton() {
        templateButton.shouldBe(visible)
                .shouldBe(visible)
                .shouldHave(text("Сделать по шаблону"));
        return this;
    }

    public BoardCreationForm checkAgreementText() {
        agreementText.shouldBe(visible)
                .shouldHave(text("Используя изображения с сайта Unsplash, вы принимаете его"));
        return this;
    }

    public BoardCreationForm checkUseTermsLink() {
        useTermsLink.shouldBe(visible).shouldHave(text("Условия использования"));
        return this;
    }

    public BoardCreationForm checkLicensesRulesLink() {
        licensesRulesLink.shouldBe(visible).shouldHave(text("правила лицензии"));
        return this;
    }

    public BoardCreationForm checkCloseButton() {
        closeButton.shouldBe(visible);
        return this;
    }

    public BoardCreationForm fillBoardNameField(String name) {
        boardNameField.setValue(name);
        return this;
    }

    public BoardCreationForm clickCreateButton() {
        createButton.click();
        return this;
    }
}



