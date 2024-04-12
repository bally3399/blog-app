package africa.semicolon.blog.dtos.response;

import lombok.Data;

@Data
public class CreateCommentResponse {
    private String commenter;
    private String comment;
    private String id;
    private String title;
}
