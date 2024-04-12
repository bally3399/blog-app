package africa.semicolon.blog.data.repositories;

import africa.semicolon.blog.data.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    Post findByTitle(String title);

    Post findByAuthor(String username);
}
