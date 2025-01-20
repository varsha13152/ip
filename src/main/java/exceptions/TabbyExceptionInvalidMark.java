package exceptions;

public class TabbyExceptionInvalidMark extends TabbyException {
    public TabbyExceptionInvalidMark() {
        super("Invalid command format. Expected: mark/unmark <task number>");
    }
}
