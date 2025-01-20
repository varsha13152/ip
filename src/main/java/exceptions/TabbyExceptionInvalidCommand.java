package exceptions;

public class TabbyExceptionInvalidCommand extends TabbyException {
    public TabbyExceptionInvalidCommand() {
        super("Invalid Command. Here are the valid commands: mark, unmark, remove, list, todo, event, deadline, view");
    }
}
