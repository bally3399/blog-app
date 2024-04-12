package africa.semicolon.blog.dtos.response;

import lombok.Data;

@Data
public class EditPostResponse {
    private String username;
    private String message;
    private String title;
    private String content;
    private String dateEdited;

}
