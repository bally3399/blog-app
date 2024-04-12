package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.CreateCommentResponse;
import africa.semicolon.blog.dtos.response.CreatePostResponse;
import africa.semicolon.blog.dtos.response.EditPostResponse;
import africa.semicolon.blog.dtos.response.ViewResponse;

import java.util.List;

public interface PostServices {
    CreatePostResponse createPost(CreatePostRequest createPost);

    Post findPostByTitle(String title);

    List<Post> findAllPosts();

    EditPostResponse editPost(EditPostRequest editPost);

    String deletePost(DeletePostRequest deletePostRequest);

    CreateCommentResponse createComment(CreateCommentRequest commentRequest);

    String deleteComment(DeleteCommentRequest deleteCommentRequest);

    ViewResponse view(ViewRequest viewRequest);
}
