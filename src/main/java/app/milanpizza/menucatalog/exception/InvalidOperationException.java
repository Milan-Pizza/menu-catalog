package app.milanpizza.menucatalog.exception;

import java.io.Serial;

public class InvalidOperationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
