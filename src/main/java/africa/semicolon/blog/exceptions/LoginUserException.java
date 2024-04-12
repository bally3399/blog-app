package africa.semicolon.blog.exceptions;

public class LoginUserException extends BlogAppException {
    public LoginUserException(String youMustBeLoggedIn) {
        super(youMustBeLoggedIn);
    }
}
