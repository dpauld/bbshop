package group7.exception;

public class OrderUpdateException extends RuntimeException{

    public OrderUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
