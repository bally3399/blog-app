package africa.semicolon.blog.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Views")
public class View {
    private String id;
    private LocalDateTime dateViewed = LocalDateTime.now();
    @DBRef
    private User viewers;
}
