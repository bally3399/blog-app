package africa.semicolon.blog.controllers;

import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.*;
import africa.semicolon.blog.exceptions.BlogAppException;
import africa.semicolon.blog.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.InputMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/User")
@AllArgsConstructor
public class UserController {
    private final UserServices userServices;
    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            RegisterUserResponse result = userServices.registerUser(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest){
        try{
            LoginResponse result = userServices.login(loginUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutUserRequest logoutUserRequest){
        try{
            LogoutUserResponse result = userServices.logout(logoutUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest){
        try{
            CreatePostResponse result = userServices.createPost(createPostRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/editPost")
    public ResponseEntity<?> editPost(@RequestBody EditPostRequest editPostRequest){
        try{
            EditPostResponse result = userServices.editPost(editPostRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestBody DeletePostRequest deletePostRequest){
        try{
            String result = userServices.deletePost(deletePostRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        try{
            String result = userServices.createComment(createCommentRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestBody LogoutUserRequest logoutUserRequest){
        try{
            LogoutUserResponse result = userServices.logout(logoutUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (BlogAppException | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }



}
