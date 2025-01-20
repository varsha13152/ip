package exceptions;

public class TabbyExceptionInvalidDeadlineInput extends TabbyException {
    public TabbyExceptionInvalidDeadlineInput() {
        super("Invalid deadline format. Use: deadline <description> /by <date time>");
    }
}

