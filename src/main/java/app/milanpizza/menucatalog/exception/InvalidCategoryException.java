package app.milanpizza.menucatalog.exception;

import java.io.Serial;

public class InvalidCategoryException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCategoryException(String message) {
        super(message);
    }

    public InvalidCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
