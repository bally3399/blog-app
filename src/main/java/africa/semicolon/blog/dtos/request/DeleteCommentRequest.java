package africa.semicolon.blog.dtos.request;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private String username;
    private String id;
}
