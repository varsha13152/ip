package exceptions;

public class TabbyExceptionInvalidTodo extends TabbyException {
    public TabbyExceptionInvalidTodo() {
        super("Invalid command format. Expected: todo <description>");
    }
}

