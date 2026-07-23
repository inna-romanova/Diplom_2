package data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetIngredientsResponse {
    private String success;
    private List<Data> data;

    public GetIngredientsResponse(){}
}
