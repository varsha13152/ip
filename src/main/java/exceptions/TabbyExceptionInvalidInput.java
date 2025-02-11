/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the input provided for commands like mark, unmark, or delete is invalid
 * or does not follow the expected format.
 */
public class TabbyExceptionInvalidInput extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidInput with a default error message.
     * The error message specifies the correct format for commands requiring a task number.
     */
    public TabbyExceptionInvalidInput() {
        super("Invalid command format. Expected: mark/unmark/delete <task number>");
    }
}
