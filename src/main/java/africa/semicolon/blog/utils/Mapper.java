package africa.semicolon.blog.utils;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        return user;
    }
    public static RegisterUserResponse map(User savedUser){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(savedUser.getUsername());
        registerUserResponse.setDate(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedUser.getTime()));
        registerUserResponse.setMessage("Successfully registered");
        return registerUserResponse;

    }

    public static LoginResponse mapLogin(User savedUser) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(savedUser.getUsername());
        loginResponse.setMessage("Login Successfully");
        return loginResponse;
    }

    public static Post map(CreatePostRequest createPostRequest){
        Post post = new Post();
        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());
        post.setAuthor(createPostRequest.getAuthor());
        return post;
    }
    public static CreatePostResponse map(Post savePost){
        CreatePostResponse response = new CreatePostResponse();
        response.setTitle(savePost.getTitle());
        response.setContent(savePost.getContent());
        response.setDate(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savePost.getDateCreated()));
        return response;
    }

    public static EditPostResponse mapEdit(Post savedPost){
        EditPostResponse response = new EditPostResponse();
        response.setUsername(savedPost.getAuthor());
        response.setContent(savedPost.getContent());
        response.setDateEdited(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(LocalDateTime.now()));
        response.setTitle(savedPost.getTitle());
        response.setMessage("Edit Post Successful");
        return response;
    }

    public static void mapEdit(Post updatePost, EditPostRequest editPost){
        updatePost.setTitle(editPost.getNewTitle());
        updatePost.setAuthor(editPost.getAuthor());
        updatePost.setContent(editPost.getNewContent());
    }
    public static Comment map(CreateCommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setTimeCreated(LocalDateTime.now());
        comment.setCommenter(commentRequest.getCommenter());
        comment.setComment(commentRequest.getComment());
        return comment;
    }

    public static CreateCommentResponse map(Comment newComment){
        CreateCommentResponse response = new CreateCommentResponse();
        response.setCommenter(newComment.getCommenter());
        response.setComment(newComment.getComment());
        return response;
    }
    public static View map(ViewRequest viewRequest) {
        View view = new View();
        view.setDateViewed(LocalDateTime.now());
        view.setViewers(viewRequest.getViewer());
        return view;
    }

}
