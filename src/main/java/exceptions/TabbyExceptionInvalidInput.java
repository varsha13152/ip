package exceptions;

public class TabbyExceptionInvalidInput extends TabbyException {
    public TabbyExceptionInvalidInput() {
        super("Invalid command format. Expected: mark/unmark/delete <task number>");
    }
}
