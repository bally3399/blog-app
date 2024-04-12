package africa.semicolon.blog.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document("Comments")
public class Comment {
    @Id
    private String id;
    private String commenter;
    private String comment;
    private LocalDateTime timeCreated = LocalDateTime.now();
}
