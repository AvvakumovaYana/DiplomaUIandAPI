package helpers;

import com.codeborne.selenide.WebDriverRunner;
import models.BoardModel;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static specs.ResponseSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;

public class TrelloAPI {
    public BoardModel createBoard(String name) {
        return given(requestSpec)
                .when()
                .post("/boards/?name=" + name)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(BoardModel.class);
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

    public void getBoard400(String id) {
        given(requestSpec)
                .when()
                .get("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(400);
    }

    public void deleteBoard(String id) {
        given(requestSpec)
                .when()
                .delete("/boards/" + id)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

    public static String getBoardId(String boardUrl) throws Exception {
        var parts = boardUrl.split(Pattern.quote("/"));
        if (parts.length != 6)
            throw new Exception(String.format("Wrong format of url \"%s\" count of parts is %s", boardUrl, parts.length));
        return parts[4];
    }
}
