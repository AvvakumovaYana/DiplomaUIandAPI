package tests.api;

import helpers.api.trello.Boards;
import helpers.api.trello.Cards;
import helpers.api.trello.Lists;
import models.BoardModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDateTime;

public class ApiTestBase {
    protected final static Boards boardsApi = new Boards();
    protected final static Lists listsApi = new Lists();
    protected final static Cards cardsApi = new Cards();

    private static final String boardName = "Board for API test";
    protected static BoardModel board;

    @BeforeAll
    static void PrepareBoard() {
        board = boardsApi.createBoard(boardName);
    }

    @AfterAll
    static void DeleteBoard() {
        boardsApi.deleteBoard(board);
    }

    protected String getListName() {
        return getName("New list");
    }

    protected String getCardName() {
        return getName("New card");
    }

    private String getName(String prefix) {
        return prefix + " " + LocalDateTime.now();
    }
}
