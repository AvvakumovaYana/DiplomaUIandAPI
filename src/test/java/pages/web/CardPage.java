package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private final SelenideElement cardLabel = $("[data-testid='card-back-title-input']");
    private final SelenideElement cardCloseButton = $("[data-testid='CloseIcon']");
    private final SelenideElement cardArchiveButton = $("[data-testid='card-back-archive-button']");
    private final SelenideElement cardDeleteButton = $("[data-testid='card-back-delete-card-button']");
    private final SelenideElement cardNextDeleteButton = $("[data-testid='popover-confirm-button']");

    public CardPage fillCardLabel(String cardName) {
        cardLabel.click();
        cardLabel.clear();
        cardLabel.setValue(cardName);
        cardLabel.pressEnter();
        return this;
    }

    public CardPage clickCardCloseButton() {
        cardCloseButton.click();
        return this;
    }

    public CardPage clickCardArchiveButton() {
        cardArchiveButton.click();
        return this;
    }

    public CardPage clickCardDeleteButton() {
        cardDeleteButton.click();
        return this;
    }

    public CardPage clickCardNextDeleteButton() {
        cardNextDeleteButton.click();
        return this;
    }
}
