package helpers.api.trello;

import models.BoardModel;
import models.ListModel;

import static io.restassured.RestAssured.given;
import static specs.HttpSpec.requestSpec;
import static specs.HttpSpec.responseSpec;

public class Boards {
    public BoardModel createBoard(String name, boolean withDefaultLists) {
        return given(requestSpec)
                .when()
                .post("/boards/?name=" + name + "&defaultLists=" + withDefaultLists)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
    }

    public BoardModel createBoard(String name) {
        return createBoard(name, false);
    }

    public BoardModel getBoard(String id) {
        return given(requestSpec)
                .when()
                .get("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
    }

    public boolean isBoardExists(String id) {
        var result = given(requestSpec)
                .when()
                .get("/boards/" + id)
                .then()
                .spec(responseSpec);
        return result.extract().statusCode() == 200;
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
                .statusCode(200);
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
