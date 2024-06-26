package africa.semicolon.blog.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime time = LocalDateTime.now();
    private boolean isLoggedIn;
    @DBRef
    private List<Post> posts = new ArrayList<>();
}
