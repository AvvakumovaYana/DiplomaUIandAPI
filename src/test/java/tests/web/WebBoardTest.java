package tests.web;

import helpers.api.trello.Boards;
import helpers.api.trello.Cards;
import helpers.api.trello.Lists;
import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import models.BoardModel;
import models.CardModel;
import models.ListModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.web.BoardCreationForm;
import pages.web.BoardPage;
import pages.web.CardPage;
import pages.web.MainBoardsPage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("web")
public class WebBoardTest extends TestBase {

    private final MainBoardsPage mainBoardsPage = new MainBoardsPage();
    private final BoardCreationForm boardCreationForm = new BoardCreationForm();
    private final Boards boardsApi = new Boards();
    private final Lists listsApi = new Lists();
    private final Cards cardsApi = new Cards();

    @Test
    @AllureId("34133")
    @DisplayName("Проверка формы создания доски")
    void boardCreationFormTest() {
        step("Открываем страницу c досками пользователя", () -> {
            mainBoardsPage.openPage();
        });
        step("Открываем форму создания доски", () -> {
            mainBoardsPage.clickCreateBoardButton();
        });
        step("Проверяем содержание формы создания доски", () -> {
            boardCreationForm
                    .checkFormTitle()
                    .checkBoardPreview()
                    .checkLabelBackground()
                    .checkBackgroundTypes()
                    .checkBoardNameLabel()
                    .checkBoardNameField()
                    .checkSetBoardNameLabel()
                    .checkBoardVisibilityLabel()
                    .checkBoardVisibilityField()
                    .checkCreateButton()
                    .checkTemplateButton()
                    .checkAgreementText()
                    .checkUseTermsLink()
                    .checkLicensesRulesLink()
                    .checkCloseButton();
        });
    }

    @Test
    @AllureId("34132")
    @DisplayName("Проверка создания доски c типом видимости: рабочее пространство")
    void boardCreationTest() {
        BoardPage boardPage = new BoardPage("Board creation test UI");

        step("Открываем страницу c досками пользователя", () -> {
            mainBoardsPage.openPage();
        });
        step("Открываем форму создания доски", () -> {
            mainBoardsPage.clickCreateBoardButton();
        });
        step("Заполняем форму и создаем доску", () -> {
            boardCreationForm
                    .checkFormTitle()
                    .fillBoardNameField(boardPage.getName())
                    .checkBoardVisibilityField()
                    .clickCreateButton();
        });
        String boardId = step("Проверяем форму созданной доски", () -> {
            return boardPage
                    .checkBoardTitle()
                    .checkFirstColumnTitle()
                    .checkSecondColumnTitle()
                    .checkThirdColumnTitle()
                    .getBoardId();
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(boardId);
        });
    }

    @Test
    @AllureId("34131")
    @DisplayName("Проверка редактирования названия доски c типом видимости: рабочее пространство")
    void boardUpdateTest() {
        BoardPage boardPage = new BoardPage("Board creation test API");

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Редактируем название доски", () -> {
            boardPage.changeBoardTitle("Board update test UI");
        });
        step("Проверяем, что название доски изменилось", () -> {
            boardPage.checkBoardTitle();
        });
        step("Проверяем изменение названия доски через API", () -> {
            BoardModel getResponse = boardsApi.getBoard(boardPage.getBoardId());
            assertThat(getResponse.getName()).isEqualTo(boardPage.getName());
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(createdBoard);
        });
    }

    @Test
    @AllureId("34134")
    @DisplayName("Проверка удаления доски c типом видимости: рабочее пространство")
    void boardDeleteTest() {
        BoardPage boardPage = new BoardPage("Board delete test UI");

        BoardModel createResponse = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createResponse);
        });
        step("Удаляем доску через меню", () -> {
            boardPage
                    .openBoardMenu()
                    .clickCloseBoard()
                    .checkCloseBoardFormTitle()
                    .checkCloseBoardFormText()
                    .checkCloseBoardFormLink()
                    .checkFormCloseButton()
                    .clickCloseBoardButton()
                    .clickDeleteBoard()
                    .checkDeleteBoardFormTitle()
                    .checkDeleteBoardFormText()
                    .checkFormCloseButton()
                    .clickDeleteBoardButton();
        });
        step("Проверяем, что отображется страница досок пользователя", () -> {
            mainBoardsPage.checkPageTitle();
        });
        step("Проверяем через API, что доски нет", () -> {
            boardsApi.getBoard(boardPage.getBoardId(), 400);
        });
    }

    @Test
    @AllureId("34387")
    @DisplayName("Проверка создания карточки на доске")
    void cardCreationTest() {
        var cardName = "Test card";
        BoardPage boardPage = new BoardPage("Board creation test API");

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Нажимаем кнопку 'Добавить карточку'", () -> {
            boardPage.clickCreateCardButton();
        });
        step("Заполняем поле 'Введите имя этой карточки…'", () -> {
            boardPage.fillCardNameField(cardName);
        });
        step("Нажимаем кнопку 'Добавить карточку'", () -> {
            boardPage.clickAddCardButton();
        });
        step("Проверяем наличие карточки через API", () -> {
            var lists = boardsApi.getLists(createdBoard);
            var listCards = new LinkedList<CardModel[]>();
            for (var list : lists) {
                var cards = listsApi.getCards(list);
                listCards.add(cards);
            }
            List<CardModel> cards = listCards.stream().flatMap(Arrays::stream).toList();
            assertThat(cards).anyMatch(c -> c.getName().equals(cardName));
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(createdBoard);
        });
    }

    @Test
    @AllureId("34386")
    @DisplayName("Проверка редактирования карточки на доске")
    void cardUpdateTest() {
        BoardPage boardPage = new BoardPage("Board creation test API");
        CardPage cardPage = new CardPage();
        String initialCardName = "New card";
        String newCardName = "Updated card";

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        ListModel createdList = step("Создаем колонку через API", () -> {
            return listsApi.createList("New list", createdBoard);
        });
        CardModel createdCard = step("Создаем карточку через API", () -> {
            return cardsApi.createCard(initialCardName, createdList);
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Проверяем карточку 'New card'", () -> {
            boardPage.checkCardLabel(initialCardName);
        });
        step("Нажимаем на карточку 'New card'", () -> {
            boardPage.clickCardLabel();
        });
        step("Меняем название карточки на 'Updated card'", () -> {
            cardPage.fillCardLabel(newCardName);
        });
        step("Нажимаем кнопку закрытия формы карточки", () -> {
            cardPage.clickCardCloseButton();
        });
        step("Проверяем название карточки", () -> {
            var updatedCard = cardsApi.getCard(createdCard);
            assertThat(updatedCard.getName()).isEqualTo(newCardName);
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(createdBoard);
        });
    }

    @Test
    @AllureId("34391")
    @DisplayName("Проверка удаления карточки на доске")
    void cardDeleteTest() {
        BoardPage boardPage = new BoardPage("Board creation test API");
        CardPage cardPage = new CardPage();
        String initialCardName = "New card";

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        ListModel createdList = step("Создаем колонку через API", () -> {
            return listsApi.createList("New list", createdBoard);
        });
        CardModel createdCard = step("Создаем карточку через API", () -> {
            return cardsApi.createCard(initialCardName, createdList);
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Проверяем карточку 'New card'", () -> {
            boardPage.checkCardLabel(initialCardName);
        });
        step("Нажимаем на карточку 'New card'", () -> {
            boardPage.clickCardLabel();
        });
        step("Нажимаем на кнопку 'Архивация'", () -> {
            cardPage.clickCardArchiveButton();
        });
        step("Нажимаем на кнопку 'Удалить'", () -> {
            cardPage.clickCardDeleteButton();
        });
        step("Нажимаем кнопку 'Удалить' на форме подтвеждения", () -> {
            cardPage.clickCardNextDeleteButton();
        });
        step("Проверяем отсутствие карточки в колонке на доске", () -> {
            boardPage.checkEmptyCard();
        });
        step("Проверяем отсутствие карточки через API", () -> {
            var card = cardsApi.getCard(createdCard);
            assertThat(card).satisfiesAnyOf(
                    c -> assertThat(c).isNull(),
                    c -> assertThat(c.getClosed()).isTrue()
            );
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(createdBoard);
        });
    }
}
