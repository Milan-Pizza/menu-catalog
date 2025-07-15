package app.milanpizza.menucatalog.exception;

public class InvalidItemTypeException extends RuntimeException {
    public InvalidItemTypeException(String message) {
        super(message);
    }

    public InvalidItemTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
