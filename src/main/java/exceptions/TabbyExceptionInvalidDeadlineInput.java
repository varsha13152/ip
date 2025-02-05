/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the input for a deadline task is invalid or does not follow the expected format.
 */
public class TabbyExceptionInvalidDeadlineInput extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidDeadlineInput with a default error message.
     * The error message specifies the correct format for entering a deadline.
     */
    public TabbyExceptionInvalidDeadlineInput() {
        super("Invalid deadline format. Use: deadline <description> /by <date time>");
    }
}
