package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.model.User;
import africa.semicolon.blog.dtos.request.CreateCommentRequest;
import africa.semicolon.blog.dtos.request.DeleteCommentRequest;
import africa.semicolon.blog.dtos.request.ViewRequest;
import africa.semicolon.blog.dtos.response.CreateCommentResponse;
import africa.semicolon.blog.dtos.response.ViewResponse;

public interface CommentServices {
    CreateCommentResponse createComment(CreateCommentRequest commentRequest);

    Comment findByCommenter(String username);

    String deleteComment(DeleteCommentRequest deleteCommentRequest);

    Comment findBy(String id);
}
