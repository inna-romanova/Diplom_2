package data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class OrderData {
    private ArrayList<String> ingredients;

    public OrderData(ArrayList<String> ingredients){
        this.ingredients = ingredients;
    }

}
