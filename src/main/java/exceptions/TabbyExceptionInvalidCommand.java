package exceptions;

public class TabbyExceptionInvalidCommand extends TabbyException {
    public TabbyExceptionInvalidCommand() {
        super("Invalid Command. Here are the valid commands: mark, unmark, delete, list, todo, event, deadline");
    }
}
