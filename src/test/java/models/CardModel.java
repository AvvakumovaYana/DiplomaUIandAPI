package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardModel {
    String id;
    String name;
    Boolean closed;
    String idBoard;
}
