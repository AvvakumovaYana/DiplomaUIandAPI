package tests.api;

import helpers.api.trello.Boards;
import helpers.api.trello.Cards;
import helpers.api.trello.Lists;
import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import models.BoardModel;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("api")
public class ApiTests {
    private final static Boards boardsApi = new Boards();
    private final static Lists listsApi = new Lists();
    private final static Cards cardsApi = new Cards();

    private static final String boardName = "Board for API test";
    private static BoardModel board;

    @BeforeAll
    static void PrepareBoard() {
        board = boardsApi.createBoard(boardName);
    }

    @AfterAll
    static void DeleteBoard() {
        boardsApi.deleteBoard(board);
    }

    @Test
    @AllureId("34137")
    @DisplayName("Проверка редактирования доски через API")
    void boardUpdateTest() {
        String newBoardName = "Board for API test " + LocalDateTime.now();

        step("Редактируем название доски", () -> {
            boardsApi.updateBoardName(board, newBoardName);
        });
        step("Проверяем изменение названия доски", () -> {
            board = boardsApi.getBoard(board.getId());
            assertThat(board.getName()).isEqualTo(newBoardName);
        });
    }

    @Test
    @DisplayName("Проверка создания колонки через API")
    void listCreateTest() {
        var newListName = getListName();

        step("Добавляем новую колонку на доску", () -> {
            listsApi.createList(newListName, board);
        });

        step("Проверяем наличие новой колонки", () -> {
            var lists = boardsApi.getLists(board);
            assertThat(lists).anyMatch(l -> l.getName().equals(newListName));
        });
    }

    @Test
    @DisplayName("Проверка удаления колонки через API")
    void listDeleteTest() {
        var newListName = getListName();

        var list = step("Добавляем новую колонку на доску", () -> {
            return listsApi.createList(newListName, board);
        });

        step("Удаляем новую колонку с доски", () -> {
            listsApi.archiveList(list);
        });

        step("Проверяем отсутствие новой колонки", () -> {
            var lists = boardsApi.getLists(board);
            assertThat(lists).noneMatch(l -> l.getName().equals(newListName));
        });
    }

    @Test
    @DisplayName("Проверка создания карточки через API")
    void cardCreateTest() {
        var newListName = getListName();
        var newCardName = getCardName();

        var list = step("Добавляем новую колонку на доску", () -> {
            return listsApi.createList(newListName, board);
        });

        step("Добавляем новую карточку в колонку", () -> {
            cardsApi.createCard(newCardName, list);
        });

        step("Проверяем наличие карточки", () -> {
            var cards = listsApi.getCards(list);
            assertThat(cards).anyMatch(c -> c.getName().equals(newCardName));
        });
    }

    @Test
    @DisplayName("Проверка удаления карточки через API")
    void cardDeleteTest() {
        var newListName = getListName();
        var newCardName = getCardName();

        var list = step("Добавляем новую колонку на доску", () -> {
            return listsApi.createList(newListName, board);
        });

        var card = step("Добавляем новую карточку в колонку", () -> {
            return cardsApi.createCard(newCardName, list);
        });

        step("Удаляем новую карточку из колонки", () -> {
            cardsApi.deleteCard(card);
        });

        step("Проверяем отсутствие карточки", () -> {
            var cards = listsApi.getCards(list);
            assertThat(cards).noneMatch(c -> c.getName().equals(newCardName));
        });
    }

    private String getListName() {
        return "New list " + LocalDateTime.now();
    }

    private String getCardName() {
        return "New card " + LocalDateTime.now();
    }
}
