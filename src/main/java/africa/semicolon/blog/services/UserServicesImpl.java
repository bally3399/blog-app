package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.data.repositories.UserRepository;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.*;
import africa.semicolon.blog.exceptions.IncorrectPassword;
import africa.semicolon.blog.exceptions.InvalidUsernameException;
import africa.semicolon.blog.exceptions.LoginUserException;
import africa.semicolon.blog.exceptions.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.blog.utils.Mapper.map;
import static africa.semicolon.blog.utils.Mapper.mapLogin;

@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostServices postServices;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        request.setUsername(request.getUsername());
        validate(request.getUsername());
        User user = map(request);
        RegisterUserResponse response = map(user);
        userRepository.save(user);
        return response;
    }

    @Override
    public LoginResponse login(LoginUserRequest loginRequest) {
        User newUser = userRepository.findByUsername(loginRequest.getUsername());
        if (!newUser.getPassword().equals(loginRequest.getPassword())) {
            throw new IncorrectPassword("Incorrect password");
        }
        newUser.setLoggedIn(true);
        LoginResponse response = mapLogin(newUser);
        userRepository.save(newUser);
        return response;
    }

    @Override
    public CreatePostResponse createPost(CreatePostRequest createPost) {
        User user = userRepository.findByUsername(createPost.getAuthor());
        if(!user.isLoggedIn()) throw new LoginUserException("You must be logged in");
        CreatePostResponse postResponse = postServices.createPost(createPost);
        Post post = postServices.findPostByTitle(createPost.getTitle());
        List<Post> posts = user.getPosts();
        posts.add(post);
        user.setPosts(posts);
        userRepository.save(user);
        return postResponse;
    }

    @Override
    public List<Post> findPostForUser(String username) {
        List<Post> posts = new ArrayList<>();
        for(Post post:postServices.findAllPosts()){
            if(post.getAuthor().equals(username)){
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public EditPostResponse editPost(EditPostRequest editPost) {
        return postServices.editPost(editPost);
    }

    @Override
    public String deletePost(DeletePostRequest deletePostRequest) {
        return postServices.deletePost(deletePostRequest);
    }

    @Override
    public User findByUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public CreateCommentResponse createComment(CreateCommentRequest commentRequest) {
        User user = userRepository.findByUsername(commentRequest.getCommenter());
        if(!user.getUsername().equals(commentRequest.getCommenter())) throw new InvalidUsernameException("Enter username");
        CreateCommentResponse response = postServices.createComment(commentRequest);
        userRepository.save(user);
        return response;
    }

    @Override
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        return postServices.deleteComment(deleteCommentRequest);
    }

    @Override
    public ViewResponse view(ViewRequest viewRequest) {
        return postServices.view(viewRequest);
    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutRequest) {
        User user = userRepository.findByUsername(logoutRequest.getUsername());
        if (user == null) throw new IncorrectPassword("Username is not valid");
        LogoutUserResponse userResponse = new LogoutUserResponse();
        userResponse.setMessage("Logout successful");
        user.setLoggedIn(false);
        return userResponse;
    }

    private void validate(String username) {
        boolean usernameExist = userRepository.existsByUsername(username);
        if(usernameExist) throw new UsernameAlreadyExistsException(String.format("%s username already exists", username));
    }
}
