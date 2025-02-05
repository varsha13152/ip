/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the input for an event task is invalid or does not follow the expected format.
 */
public class TabbyExceptionInvalidEventInput extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidEventInput with a default error message.
     * The error message specifies the correct format for entering an event task.
     */
    public TabbyExceptionInvalidEventInput() {
        super("Invalid event format. Use: event <description> /from <start> /to <end>");
    }
}
