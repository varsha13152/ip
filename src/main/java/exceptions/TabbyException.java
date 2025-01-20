package exceptions;

/**
 * Custom exception class for handling errors.
 */
public class TabbyException extends Exception {
    /**
     * Constructs a new {@code TabbyException} with the specified error message.
     *
     * @param response The detailed error message.
     */
    protected TabbyException(String response) {
        super(response);
    }
}
