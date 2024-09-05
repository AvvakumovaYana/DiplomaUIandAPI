package helpers.api.trello;

import models.BoardModel;
import models.CardModel;
import models.ListModel;

import static io.restassured.RestAssured.given;
import static specs.HttpSpec.requestSpec;
import static specs.HttpSpec.responseSpec;

public class Lists {
    public ListModel createList(String name, BoardModel board) {
        return given(requestSpec)
                .when()
                .post("/lists?name=" + name + "&idBoard=" + board.getId())
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(ListModel.class);
    }

    public void archiveList(ListModel list) {
        given(requestSpec)
                .when()
                .put("/lists/" + list.getId() + "/closed?value=true")
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

    public CardModel[] getCards(ListModel list) {
        return given(requestSpec)
                .when()
                .get("/lists/" + list.getId() + "/cards")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(CardModel[].class);
    }
}
