package group7.exception;

public class MissingAddressException extends RuntimeException {
    public MissingAddressException(String message) {
        super(message);
    }
}
