/**
 * This package contains custom exceptions used in the Tabby application to handle specific error cases.
 */
package exceptions;

/**
 * Exception thrown when the provided task number is invalid or does not correspond to any existing task.
 */
public class TabbyExceptionInvalidTaskNumber extends TabbyException {

    /**
     * Constructs a new TabbyExceptionInvalidTaskNumber with a default error message.
     * The error message provides guidance on how to retrieve existing tasks and correctly mark, unmark, or delete them.
     */
    public TabbyExceptionInvalidTaskNumber() {
        super("Invalid Task Number. Please use 'list' to retrieve existing tasks. "
                + "To mark or unmark a task please use: mark/unmark/delete <task number> ");
    }
}

