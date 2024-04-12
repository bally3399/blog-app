package africa.semicolon.blog.dtos.request;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String author;
    private String title;
    private String content;
}
