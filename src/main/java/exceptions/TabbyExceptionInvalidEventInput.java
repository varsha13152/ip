package exceptions;

public class TabbyExceptionInvalidEventInput extends TabbyException {
    public TabbyExceptionInvalidEventInput() {
        super("Invalid event format. Use: event <description> /from <start> /to <end>");
    }
}

