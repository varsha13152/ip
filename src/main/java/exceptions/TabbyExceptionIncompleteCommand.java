package exceptions;

/**
 * Exception thrown when a command is incomplete or missing required parts.
 */
public class TabbyExceptionIncompleteCommand extends TabbyException {
    /**
     * Constructs an {@code TabbyExceptionIncompleteCommand} with a detailed error message.
     */
    public TabbyExceptionIncompleteCommand() {
        super("Invalid Command. Here are the valid commands & the respective formats:\n" +
                "  - Mark: mark <task number>\n" +
                "  - Unark: unmark <task number>\n" +
                "  - Delete: delete <task number>\n" +
                "  - Deadline: deadline <description> /by <date time>\n" +
                "  - Event: event <description> /from <start> /to <end>\n" +
                "  - Todo: todo <description>");
    }
}