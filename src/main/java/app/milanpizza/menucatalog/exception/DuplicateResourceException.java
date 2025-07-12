package app.milanpizza.menucatalog.exception;

import java.io.Serial;

public class DuplicateResourceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
