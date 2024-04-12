package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    RegisterUserResponse registerUser(RegisterUserRequest request);

    LoginResponse login(LoginUserRequest loginRequest);

    CreatePostResponse createPost(CreatePostRequest createPost);

    List<Post> findPostForUser(String username);

    EditPostResponse editPost(EditPostRequest editPost);

    String deletePost(DeletePostRequest deletePostRequest);

    User findByUser(String username);

    CreateCommentResponse createComment(CreateCommentRequest commentRequest);

    String deleteComment(DeleteCommentRequest deleteCommentRequest);

    ViewResponse view(ViewRequest viewRequest);

    LogoutUserResponse logout(LogoutUserRequest logoutRequest);
}
