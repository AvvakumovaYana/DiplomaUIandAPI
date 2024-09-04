package helpers.api;

import models.BoardModel;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static specs.HttpSpec.requestSpec;
import static specs.HttpSpec.responseSpec;

public class TrelloAPI {
    public static String getBoardId(String boardUrl) throws Exception {
        var parts = boardUrl.split(Pattern.quote("/"));
        if (parts.length != 6)
            throw new Exception(String.format("Wrong format of url \"%s\" count of parts is %s", boardUrl, parts.length));
        return parts[4];
    }

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

    public void deleteBoard(String id) {
        given(requestSpec)
                .when()
                .delete("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

    public void updateBoardName(String id, String name) {
        BoardModel body = new BoardModel();
        body.setName(name);
        given(requestSpec)
                .when()
                .body(body)
                .put("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
    }
}
