package africa.semicolon.blog.exceptions;

public class PostNotFoundException extends BlogAppException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
