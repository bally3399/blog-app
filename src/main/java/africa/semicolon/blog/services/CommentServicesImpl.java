package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.repositories.CommentRepository;
import africa.semicolon.blog.dtos.request.CreateCommentRequest;
import africa.semicolon.blog.dtos.request.DeleteCommentRequest;
import africa.semicolon.blog.dtos.response.CreateCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.blog.utils.Mapper.map;

@Service
public class CommentServicesImpl implements CommentServices{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public CreateCommentResponse createComment(CreateCommentRequest commentRequest) {
        Comment comment = map(commentRequest);
        CreateCommentResponse response = map(comment);
        commentRepository.save(comment);
        return response;
    }

    @Override
    public Comment findByCommenter(String username) {
        return commentRepository.findByCommenter(username);
    }

    @Override
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Comment foundComment =  commentRepository.findByCommenter(deleteCommentRequest.getUsername());
        commentRepository.delete(foundComment);
        return "Successfully";

    }

    @Override
    public Comment findBy(String id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public Comment findByTitle(String title) {
        return commentRepository.findByTitle(title);
    }


}
