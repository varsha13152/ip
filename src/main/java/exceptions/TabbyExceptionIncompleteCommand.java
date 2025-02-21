package exceptions;

/**
 * Exception thrown when a command is incomplete or missing required parts.
 */
public class TabbyExceptionIncompleteCommand extends TabbyException {
    /**
     * Constructs an {@code TabbyExceptionIncompleteCommand} with a detailed error message.
     */
    public TabbyExceptionIncompleteCommand() {
        super("Invalid Command. Here are the valid commands & the respective formats:\n"
                + "  - List\n"
                + "  - Reminder\n"
                + "  - Find <keyword>\n"
                + "  - Mark: mark <task number>\n"
                + "  - Unark: unmark <task number>\n"
                + "  - Delete: delete <task number>\n"
                + "  - Deadline: deadline <description> /by <dd/mm/yyyy> <hhmm> \n"
                + "  - Event: event <description> /from <dd/mm/yyyy> <hhmm> /to <dd/mm/yyyy> <hhmm> \n"
                + "  - Todo: todo <description>");
    }
}

