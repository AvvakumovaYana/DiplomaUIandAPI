package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private final SelenideElement cardLabel = $("[data-testid='card-back-title-input']");
    private final SelenideElement cardCloseButton = $("[data-testid='CloseIcon']");
    private final SelenideElement cardArchiveButton = $("[data-testid='card-back-archive-button']");
    private final SelenideElement cardDeleteButton = $("[data-testid='card-back-delete-card-button']");
    private final SelenideElement cardNextDeleteButton = $("[data-testid='popover-confirm-button']");

    public void fillCardLabel(String cardName) {
        cardLabel.click();
        cardLabel.clear();
        cardLabel.setValue(cardName);
        cardLabel.pressEnter();
    }

    public void clickCardCloseButton() {
        cardCloseButton.click();
    }

    public void clickCardArchiveButton() {
        cardArchiveButton.click();
    }

    public void clickCardDeleteButton() {
        cardDeleteButton.click();
    }

    public void clickCardNextDeleteButton() {
        cardNextDeleteButton.click();
    }
}
