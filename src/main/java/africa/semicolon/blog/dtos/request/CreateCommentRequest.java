package africa.semicolon.blog.dtos.request;

import africa.semicolon.blog.data.model.User;
import lombok.Data;

@Data
public class CreateCommentRequest {
    private String id;
    private String comment;
    private String commenter;
    private String title;
}
