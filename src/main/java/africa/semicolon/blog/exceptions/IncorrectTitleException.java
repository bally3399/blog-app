package africa.semicolon.blog.exceptions;

public class IncorrectTitleException extends BlogAppException {
    public IncorrectTitleException(String titleNotFound) {
        super(titleNotFound);
    }
}
