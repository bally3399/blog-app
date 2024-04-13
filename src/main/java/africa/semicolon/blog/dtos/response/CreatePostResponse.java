package africa.semicolon.blog.dtos.response;

import lombok.Data;

@Data
public class CreatePostResponse {
    private String title;
    private String content;
    private String date;


}
