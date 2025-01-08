package group7.exception;

public class InsufficientStockException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    public InsufficientStockException(String message) {
        super(message);
    }
    public InsufficientStockException(String resourceName, String fieldName) {
        super(String.format("Insufficient stock for the %s item named %s.",resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName=fieldName;
    }
}