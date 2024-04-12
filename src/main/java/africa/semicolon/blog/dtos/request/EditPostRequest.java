package africa.semicolon.blog.dtos.request;

import lombok.Data;

@Data
public class EditPostRequest {
    private String title;
    private String content;
    private String newTitle;
    private String newContent;
    private String author;
}
