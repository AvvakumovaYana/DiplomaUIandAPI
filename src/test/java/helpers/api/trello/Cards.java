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

    public CardModel getCard(CardModel card) {
        var result = given(requestSpec)
                .when()
                .get("/cards/" + card.getId())
                .then()
                .spec(responseSpec);
        if (result.extract().statusCode() == 200)
            return result.extract().as(CardModel.class);
        return null;
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
