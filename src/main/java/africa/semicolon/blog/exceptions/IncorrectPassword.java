package africa.semicolon.blog.exceptions;

public class IncorrectPassword extends BlogAppException {
    public IncorrectPassword(String incorrectPassword) {
        super(incorrectPassword);
    }
}
