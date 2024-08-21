package helpers;

import models.BoardModel;

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
}
