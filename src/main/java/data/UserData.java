package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {

    private String email;
    private String password;
    private String name;

    public UserData(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserData(String email, String password){
        this.email = email;
        this.password = password;
    }

}
