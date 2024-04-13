package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.data.repositories.UserRepository;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.*;
import africa.semicolon.blog.exceptions.*;
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
        validateRegistration(request);
        validate(request.getUsername());
        request.setUsername(request.getUsername());
        User user = map(request);
        RegisterUserResponse response = map(user);
        userRepository.save(user);
        return response;
    }

    private static void validateRegistration(RegisterUserRequest request) {
        if(request.getUsername().matches("[a-zA-Z]")) throw new InvalidUsernameException("Username must not be empty");
        if(request.getFirstName().matches("[a-zA-Z]")) throw new InvalidUsernameException("firstname must not be empty");
        if(request.getLastName().matches("[a-zA-Z]")) throw new InvalidUsernameException("Lastname must not be empty");
        if(request.getPassword().trim().isEmpty()) throw new IncorrectPassword("password must not be empty");
    }

    @Override
    public LoginResponse login(LoginUserRequest loginRequest) {
        User newUser = userRepository.findByUsername(loginRequest.getUsername());
        if(newUser == null) throw new InvalidUsernameException("new user");
        validateLogin(loginRequest, newUser);
        newUser.setLoggedIn(true);
        LoginResponse response = mapLogin(newUser);
        userRepository.save(newUser);
        return response;
    }

    private static void validateLogin(LoginUserRequest loginRequest,User newUser) {
        if(loginRequest.getPassword().trim().isEmpty()) throw new IncorrectPassword("password must not be empty");
        if(loginRequest.getUsername().matches("[a-zA-Z]"))throw new InvalidUsernameException("Username must not be empty");
        if (!newUser.getPassword().equals(loginRequest.getPassword())) throw new IncorrectPassword("Incorrect password");

    }

    @Override
    public CreatePostResponse createPost(CreatePostRequest createPost) {
        User user = userRepository.findByUsername(createPost.getAuthor());
        validatePost(createPost, user);
        CreatePostResponse postResponse = postServices.createPost(createPost);
        Post post = postServices.findPostByTitle(createPost.getTitle());
        List<Post> posts = user.getPosts();
        posts.add(post);
        user.setPosts(posts);
        userRepository.save(user);
        return postResponse;
    }

    private static void validatePost(CreatePostRequest createPost, User user) {
        if (user == null) throw new IncorrectPassword("Username is not valid");
        if(user.getUsername().matches("[a-zA-Z]")) throw new InvalidUsernameException("Username must not be empty");
        if(user.getUsername().trim().isEmpty()) throw new InvalidUsernameException("Username must not be empty");
        if(!user.isLoggedIn()) throw new LoginUserException("You must be logged in");
        if(createPost.getAuthor().matches("[a-zA-Z]")) throw new InvalidUsernameException("Username must not be empty");
        if(createPost.getContent().trim().isEmpty()) throw new IncorrectTitleException("Content not found");
        if(createPost.getTitle().trim().isEmpty()) throw new IncorrectTitleException("Title not found");
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
    public String createComment(CreateCommentRequest commentRequest) {
        User user = userRepository.findByUsername(commentRequest.getCommenter());
        validate(commentRequest, user);
        postServices.createComment(commentRequest);
        userRepository.save(user);
        return "Successful";
    }

    private static void validate(CreateCommentRequest commentRequest, User user) {
        if(user == null) throw new InvalidUsernameException("username must not be null");
        if(user.getUsername().equals(commentRequest.getCommenter())) throw new InvalidUsernameException("Enter username");
        if(user.getUsername().matches("[a-zA-Z]+")) throw new InvalidUsernameException("Enter username");
        if(commentRequest.getCommenter().isEmpty()) throw new InvalidUsernameException("username must not be empty");
        if(commentRequest.getTitle().isEmpty()) throw new InvalidUsernameException("username must not be empty");
        if(commentRequest.getComment().isEmpty()) throw new InvalidUsernameException("comment must not be empty");
    }

    @Override
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        return postServices.deleteComment(deleteCommentRequest);
    }

    @Override
    public void view(ViewRequest viewRequest) {
        postServices.view(viewRequest);
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
