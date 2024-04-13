package africa.semicolon.blog.services;

import africa.semicolon.blog.data.model.Comment;
import africa.semicolon.blog.data.model.Post;
import africa.semicolon.blog.data.model.View;
import africa.semicolon.blog.data.repositories.PostRepository;
import africa.semicolon.blog.dtos.request.*;
import africa.semicolon.blog.dtos.response.CreateCommentResponse;
import africa.semicolon.blog.dtos.response.CreatePostResponse;
import africa.semicolon.blog.dtos.response.EditPostResponse;
import africa.semicolon.blog.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

import static africa.semicolon.blog.utils.Mapper.map;
import static africa.semicolon.blog.utils.Mapper.mapEdit;

@Service
public class PostServicesImpl implements PostServices{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentServices commentServices;
    @Autowired
    private ViewServices viewServices;
    @Override
    public CreatePostResponse createPost(CreatePostRequest createPost) {
        Post post = map(createPost);
        CreatePostResponse result = map(post);
        postRepository.save(post);
        return result;
    }

    @Override
    public Post findPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public EditPostResponse editPost(EditPostRequest editPost) {
        Post updatePost = postRepository.findByTitle(editPost.getTitle());
        validateInput(editPost, updatePost);
        if (editPost.getTitle()!= null && editPost.getContent() != null && editPost.getAuthor() != null){
            mapEdit(updatePost, editPost);
        }
        Post savedPost = postRepository.save(updatePost);
        return mapEdit(savedPost);
    }

    private static void validateInput(EditPostRequest editPost, Post updatePost) {
        if(updatePost == null) throw new PostNotFoundException("No post");
        if(editPost.getContent().trim().isEmpty()) throw new PostNotFoundException("No content");
        if(editPost.getTitle().trim().isEmpty()) throw new PostNotFoundException("No title");
        if(editPost.getNewTitle().trim().isEmpty()) throw new PostNotFoundException("No new title");
        if(editPost.getNewContent().trim().isEmpty()) throw new PostNotFoundException("No new content");
    }

    @Override
    public String deletePost(DeletePostRequest deletePostRequest) {
        if(deletePostRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        Post foundPost = postRepository.findByTitle(deletePostRequest.getTitle());
        if (foundPost != null){
            postRepository.delete(foundPost);
            return "Post Successfully Deleted";
        }
        throw new PostNotFoundException("Post not found");
    }
    @Override
    public CreateCommentResponse createComment(CreateCommentRequest commentRequest) {
        Post post = postRepository.findByTitle(commentRequest.getTitle());
        if(post != null){
            CreateCommentResponse response = commentServices.createComment(commentRequest);
            Comment comment = commentServices.findBy(commentRequest.getId());
            List<Comment> comments = post.getComments();
            comments.add(comment);
            post.setComments(comments);
            postRepository.save(post);
            return response;
        }
        throw new PostNotFoundException("Post not found");
    }


    @Override
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        commentServices.deleteComment(deleteCommentRequest);
        Post post = postRepository.findByAuthor(deleteCommentRequest.getUsername());
        if(post != null) {
            List<Comment> comments = post.getComments();
            Comment comment = commentServices.findByCommenter(deleteCommentRequest.getUsername());
            comments.remove(comment);
            post.setComments(comments);
            postRepository.save(post);
            return "Successfully";
        }
        throw new PostNotFoundException("Post not found");
    }

    @Override
    public void view(ViewRequest viewRequest) {
        Post post = postRepository.findByAuthor(viewRequest.getViewer());
        View view = viewServices.view(viewRequest);
        List<View> views = post.getViews();
        views.add(view);
        post.setViews(views);
        postRepository.save(post);
    }

}
