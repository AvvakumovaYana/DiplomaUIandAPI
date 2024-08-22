package tests.api;

import helpers.TrelloAPI;
import io.qameta.allure.Owner;
import models.BoardModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("api")

public class BoardTest {

    private TrelloAPI api = new TrelloAPI();

    @Test
    @DisplayName("Проверка создания, редактирования, удаления доски через API")
    void boardLifecycleTest() throws Exception {
        String boardName = "Board for API test";
        String newBoardName = "Board for API test (NEW)";
        BoardModel createResponse = step("Создаем доску", () -> {
            return api.createBoard(boardName);
        });
        String boardId = TrelloAPI.getBoardId(createResponse.getUrl());
        step("Редактируем название доски", () -> {
            api.updateBoardName(boardId, newBoardName);
        });
        step("Проверяем изменение названия доски", () -> {
            BoardModel getResponse = api.getBoard(boardId);
            assertThat(getResponse.getName()).isEqualTo(newBoardName);
        });
        step("Удаляем доску", () -> {
            api.deleteBoard(boardId);
        });
        step("Проверяем, что доски нет", () -> {
            api.getBoard404(boardId);
        });
    }
}
