package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.data.repositories.CommentRepository;
import africa.semicolon.blog.data.repositories.PostRepository;
import africa.semicolon.blog.data.repositories.UserRepository;
import africa.semicolon.blog.data.repositories.ViewRepository;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.exceptions.LoginUserException;
import africa.semicolon.blog.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServicesImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserServicesImpl userServicesImpl;
    @Autowired
    private ViewRepository viewRepository;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void registerUser(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        userServices.registerUser(request);
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerTwoUsers(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        userServices.registerUser(request);

        request.setUsername("username2");
        request.setPassword("Password");
        request.setFirstName("firstName2");
        request.setLastName("lastName2");
        userServices.registerUser(request);
        assertEquals(2, userRepository.count());
    }

    @Test
    public void registerUserTwiceWithSameInputTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);
        assertThrows(UsernameAlreadyExistsException.class,() -> userServices.registerUser(registerUserRequest));
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerUser_loginTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registeredUserCanCreatePostTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);
        assertEquals(1, userRepository.count());

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);
        assertEquals(1, postRepository.count());
        assertEquals(1, userServices.findPostForUser("username").size());
    }

    @Test
    public void registerUser_createPostWithoutLogin_throwLoginUserException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor(registerUserRequest.getUsername());
        assertThrows(LoginUserException.class,() ->userServices.createPost(createPost));

    }
    @Test
    public void registerTest_createPost_editPostTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);

        EditPostRequest editPost = new EditPostRequest();
        editPost.setTitle("title");
        editPost.setNewTitle("new title");
        editPost.setContent("content");
        editPost.setNewContent("new content");
        editPost.setAuthor(registerUserRequest.getUsername());
        userServices.editPost(editPost);

        assertEquals(1, postRepository.count());
        assertEquals(1, userServices.findPostForUser("username").size());
    }

    @Test
    public void registerUser_login_createPost_deletePostTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);

        DeletePostRequest deletePostRequest = new DeletePostRequest();
        deletePostRequest.setTitle("title");
        userServices.deletePost(deletePostRequest);

        assertEquals(0, postRepository.count());
        assertEquals(0, userServices.findPostForUser("username").size());

    }

    @Test
    public void registerUser_loginUser_createPost_createCommentTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);
        User user = userServices.findByUser("username");

        assertEquals(1, postRepository.count());

        assertEquals(1, userServices.findPostForUser("username").size());
        assertEquals(1, user.getPosts().size());


        CreateCommentRequest commentRequest = new CreateCommentRequest();
        commentRequest.setCommenter("username");
        commentRequest.setTitle("title");
        commentRequest.setComment("comment");
        userServices.createComment(commentRequest);


        user = userServices.findByUser("username");
        assertEquals(1, commentRepository.count());
        assertEquals(1, user.getPosts().getFirst().getComments().size());
    }

    @Test
    public void registerUser_loginUser_createPost_createComment_deleteCommentTest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);
        User user = userServices.findByUser("username");

        assertEquals(1, postRepository.count());
        assertEquals(1, userServices.findPostForUser("username").size());
        assertEquals(1, user.getPosts().size());


        CreateCommentRequest commentRequest = new CreateCommentRequest();
        commentRequest.setCommenter("username");
        commentRequest.setComment("message");
        commentRequest.setTitle("title");
        userServices.createComment(commentRequest);

        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest();
        deleteCommentRequest.setUsername("username");
        userServices.deleteComment(deleteCommentRequest);

        assertEquals(0, commentRepository.count());
        assertEquals(0, user.getPosts().getFirst().getComments().size());


    }

    @Test
    public void registerUser_loginUser_createPost_createComment_view() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        CreatePostRequest createPost = new CreatePostRequest();
        createPost.setTitle("title");
        createPost.setContent("content");
        createPost.setAuthor("username");
        userServices.createPost(createPost);
        User user = userServices.findByUser("username");

        assertEquals(1, postRepository.count());
        assertEquals(1, userServices.findPostForUser("username").size());
        assertEquals(1, user.getPosts().size());


        CreateCommentRequest commentRequest = new CreateCommentRequest();
        commentRequest.setCommenter("username");
        commentRequest.setComment("message");
        commentRequest.setTitle("title");
        userServices.createComment(commentRequest);

        ViewRequest viewRequest = new ViewRequest();
        viewRequest.setViewer(user);
        userServices.view(viewRequest);

        assertEquals(1, viewRepository.count());
    }

    @Test
    public void registerUser_login_logoutTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("firstname");
        registerUserRequest.setLastName("lastname");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");
        userServices.registerUser(registerUserRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        userServices.login(loginRequest);

        LogoutUserRequest logoutRequest = new LogoutUserRequest();
        logoutRequest.setUsername("username");
        userServices.logout(logoutRequest);
        assertTrue(userServices.findByUser("username").isLoggedIn());

    }
 }