package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String success;
    private String accessToken;
    private String refreshToken;
    private LoginResponseUser loginResponseUser;

    public LoginResponse(){}
}
