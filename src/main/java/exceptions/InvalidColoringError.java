package exceptions;

public class InvalidColoringError extends Exception{

    public InvalidColoringError(String message) {
        super(message);  // Pass the message to the superclass (Exception)
    }
}
