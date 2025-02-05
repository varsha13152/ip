/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the input for a todo task is invalid or does not follow the expected format.
 */
public class TabbyExceptionInvalidTodo extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidTodo with a default error message.
     * The error message specifies the correct format for entering a todo task.
     */
    public TabbyExceptionInvalidTodo() {
        super("Invalid command format. Expected: todo <description>");
    }
}
