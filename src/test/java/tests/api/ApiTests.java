package tests.api;

import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("api")
public class ApiTests extends ApiTestBase {
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
    @AllureId("34382")
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
    @AllureId("34384")
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
    @AllureId("34383")
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
    @AllureId("34385")
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
}
