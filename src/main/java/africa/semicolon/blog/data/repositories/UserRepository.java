package africa.semicolon.blog.data.repositories;

import africa.semicolon.blog.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
