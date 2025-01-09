package group7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderCreationException.class)
    public ResponseEntity<String> handleOrderCreationException(OrderCreationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderUpdateException.class)
    public ResponseEntity<String> handleOrderUpdateException(OrderUpdateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public String handleResourceNotFound(ResourceNotFoundException ex, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("error", ex.getMessage());
//        return "redirect:/notFound"; // 404 Not Found, notFound html page should be able to access error attribute
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // 404 Not Found
    }


    @ExceptionHandler(InsufficientStockException.class)
    public String handleInsufficientStockException(InsufficientStockException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/basket";
    }

    @ExceptionHandler(MissingAddressException.class)
    public String handleMissingAddressException(MissingAddressException ex, RedirectAttributes redirectAttributes, Principal principal) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        String username = principal.getName();
        return "redirect:/profile";
    }

    @ExceptionHandler(OrderCancellationException.class)
    public String handleOrderCancellationException(OrderCancellationException ex, RedirectAttributes redirectAttributes, Principal principal) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/orders";
    }
}
