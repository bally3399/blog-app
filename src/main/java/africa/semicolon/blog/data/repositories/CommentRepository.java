package africa.semicolon.blog.data.repositories;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment findByCommenter(String username);

    Comment findCommentById(String id);
}
