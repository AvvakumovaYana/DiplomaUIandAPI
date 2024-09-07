package helpers.api.trello;

import models.BoardModel;
import models.ListModel;

import static io.restassured.RestAssured.given;
import static specs.HttpSpec.requestSpec;
import static specs.HttpSpec.responseSpec;

public class Boards {
    public BoardModel createBoard(String name) {
        return given(requestSpec)
                .when()
                .post("/boards/?name=" + name)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
    }

    public BoardModel getBoard(String id, int statusCode) {
        var result = given(requestSpec)
                .when()
                .get("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(statusCode);
        if (statusCode == 200)
            return result.extract().as(BoardModel.class);
        return null;
    }

    public BoardModel getBoard(String id) {
        return getBoard(id, 200);
    }

    public void deleteBoard(String boardId) {
        given(requestSpec)
                .when()
                .delete("/boards/" + boardId)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

    public void deleteBoard(BoardModel board) {
        deleteBoard(board.getId());
    }

    public void updateBoardName(BoardModel board, String name) {
        BoardModel body = new BoardModel();
        body.setName(name);
        given(requestSpec)
                .when()
                .body(body)
                .put("/boards/" + board.getId())
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
    }

    public ListModel[] getLists(BoardModel board) {
        return given(requestSpec)
                .when()
                .get("/boards/" + board.getId() + "/lists")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(ListModel[].class);
    }
}
