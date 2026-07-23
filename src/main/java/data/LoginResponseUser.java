package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseUser {
    private String email;
    private String name;

    public LoginResponseUser(){}

}
