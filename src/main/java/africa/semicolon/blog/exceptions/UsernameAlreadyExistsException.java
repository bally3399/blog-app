package africa.semicolon.blog.exceptions;

public class UsernameAlreadyExistsException extends BlogAppException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
