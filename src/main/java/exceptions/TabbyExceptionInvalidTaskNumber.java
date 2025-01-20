package exceptions;

public class TabbyExceptionInvalidTaskNumber extends TabbyException {
    public TabbyExceptionInvalidTaskNumber() {
        super("Invalid Task Number. Please use 'list' to retrieve existing tasks. " +
                "To mark or unmark a task please use: mark/unmark <task number> ");
    }
}

