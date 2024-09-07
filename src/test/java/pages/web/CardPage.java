package pages.web;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private final SelenideElement cardLabel = $("[data-testid='card-back-title-input']");
    private final SelenideElement cardCloseButton = $("[data-testid='CloseIcon']");
    private final SelenideElement cardArchiveButton = $("[data-testid='card-back-archive-button']");
    private final SelenideElement cardDeleteButton = $("[data-testid='card-back-delete-card-button']");
    private final SelenideElement cardNextDeleteButton = $("[data-testid='popover-confirm-button']");

    @Step("Заполняем карточку")
    public void fillCardLabel(String cardName) {
        cardLabel.click();
        cardLabel.clear();
        cardLabel.setValue(cardName);
        cardLabel.pressEnter();
    }

    @Step("Нажимаем кнопку закрытия карточки")
    public void clickCardCloseButton() {
        cardCloseButton.click();
    }

    @Step("Нажимаем кнопку архивирования карточки")
    public void clickCardArchiveButton() {
        cardArchiveButton.click();
    }

    @Step("Нажимаем кнопку удаления карточки")
    public void clickCardDeleteButton() {
        cardDeleteButton.click();
    }

    @Step("Нажимаем кнопку подтверждения удаления карточки")
    public void clickCardNextDeleteButton() {
        cardNextDeleteButton.click();
    }
}
