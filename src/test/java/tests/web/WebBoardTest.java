package tests.web;

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

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("web")
public class WebBoardTest extends TestBase {
    protected final MainBoardsPage mainBoardsPage = new MainBoardsPage();
    protected final BoardCreationForm boardCreationForm = new BoardCreationForm();

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
                    .fillBoardNameField(boardPage.getName())
                    .clickCreateButton();
        });
        step("Проверяем название созданной доски через API", () -> {
            sleep(1000);
            BoardModel board = boardsApi.getBoard(boardPage.getBoardId());
            assertThat(board.getName()).isEqualTo(boardPage.getName());
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(boardPage.getBoardId());
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
        step("Проверяем изменение названия доски через API", () -> {
            BoardModel board = boardsApi.getBoard(boardPage.getBoardId());
            assertThat(board.getName()).isEqualTo(boardPage.getName());
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

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Удаляем доску через меню", () -> {
            boardPage
                    .openBoardMenu()
                    .getMenu()
                    .clickCloseBoard()
                    .clickCloseBoardButton()
                    .clickDeleteBoard()
                    .clickDeleteBoardButton();
        });
        step("Проверяем через API, что доски нет", () -> {
            sleep(1000);
            assertThat(boardsApi.isBoardExists(boardPage.getBoardId())).isFalse();
        });
    }

    @Test
    @AllureId("34387")
    @DisplayName("Проверка создания карточки на доске")
    void cardCreationTest() {
        var cardName = "Test card";
        BoardPage boardPage = new BoardPage("Board creation test API");

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName(), true);
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
            boardPage.clickSaveCardButton();
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
        step("Нажимаем на карточку 'New card'", () -> {
            boardPage.clickCardLabel();
        });
        step("Меняем название карточки на 'Updated card'", () -> {
            cardPage.fillCardLabel(newCardName);
        });
        step("Нажимаем кнопку закрытия формы карточки", () -> {
            cardPage.clickCardCloseButton();
        });
        step("Проверяем название карточки через API", () -> {
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
            cardPage.clickCardDeleteConfirmButton();
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

    @Test
    @AllureId("34395")
    @DisplayName("Проверка создания колонки на доске")
    void listCreationTest() {
        var listName = "Test list";
        BoardPage boardPage = new BoardPage("Board creation test API");

        BoardModel createdBoard = step("Создаем доску через API", () -> {
            return boardsApi.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createdBoard);
        });
        step("Нажимаем кнопку 'Добавить список'", () -> {
            boardPage.clickAddListButton();
        });
        step("Заполняем поле 'Введите имя колонки…'", () -> {
            boardPage.fillListNameField(listName);
        });
        step("Нажимаем кнопку 'Добавить список'", () -> {
            boardPage.clickSaveListButton();
        });
        step("Проверяем наличие колонки через API", () -> {
            var lists = boardsApi.getLists(createdBoard);
            assertThat(lists).anyMatch(l -> l.getName().equals(listName));
        });
        step("Удаляем доску через API", () -> {
            boardsApi.deleteBoard(createdBoard);
        });
    }
}
