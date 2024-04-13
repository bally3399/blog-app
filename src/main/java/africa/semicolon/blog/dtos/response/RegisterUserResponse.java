package africa.semicolon.blog.dtos.response;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String message;
    private String username;
    private String date;

}
