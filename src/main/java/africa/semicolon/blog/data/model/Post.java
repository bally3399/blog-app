package africa.semicolon.blog.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Document("Posts")
public class Post {
    private String id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated = LocalDateTime.now();
    @DBRef
    private List<View> views = new ArrayList<>();
    @DBRef
    private List<Comment> comments = new ArrayList<>();

}
