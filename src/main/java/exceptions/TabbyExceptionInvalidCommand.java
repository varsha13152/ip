/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the user input does not match any of the valid commands in the Tabby application.
 */
public class TabbyExceptionInvalidCommand extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidCommand with a default error message.
     * The error message specifies the list of valid commands available in the application.
     */
    public TabbyExceptionInvalidCommand() {
        super("Invalid Command. Here are the valid commands: mark, unmark, delete, list, todo, event, deadline,reminder");
    }
}
