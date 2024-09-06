package helpers.api.trello;

import models.CardModel;
import models.ListModel;

import static io.restassured.RestAssured.given;
import static specs.HttpSpec.requestSpec;
import static specs.HttpSpec.responseSpec;

public class Cards {
    public CardModel createCard(String name, ListModel list) {
        return given(requestSpec)
                .when()
                .post("/cards?name=" + name + "&idList=" + list.getId())
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(CardModel.class);
    }

    public CardModel getCard(CardModel card, int statusCode) {
        var result = given(requestSpec)
                .when()
                .get("/cards/" + card.getId())
                .then()
                .spec(responseSpec)
                .statusCode(statusCode);
        if (statusCode == 200)
            return result.extract().as(CardModel.class);
        return null;
    }

    public CardModel getCard(CardModel card) {
        return getCard(card, 200);
    }

    public void deleteCard(CardModel card) {
        given(requestSpec)
                .when()
                .delete("/cards/" + card.getId())
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }
}
