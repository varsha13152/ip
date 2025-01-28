package action;
import exceptions.TabbyExceptionInvalidCommand;
import exceptions.TabbyExceptionInvalidTaskNumber;
import exceptions.TabbyExceptionIncompleteCommand;
import exceptions.TabbyExceptionInvalidInput;
import exceptions.TabbyException;
import task.TaskManager;
import tabby.Ui;
/**
 * Abstract class representing an action that can be performed.
 */
public abstract class Action {
    private enum Command {
        LIST,
        MARK,
        UNMARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT
    }
    /**
     * Parses the user input and returns the appropriate Action object.
     *
     * @param input The user's input string.
     * @return An Action object representing the parsed command.
     * @throws TabbyExceptionInvalidCommand If the command is invalid.
     * @throws TabbyExceptionInvalidInput If the mark command is invalid.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     */
    public static Action userAction(String input, boolean isDone, boolean isUserInput, Ui ui) throws
            TabbyExceptionInvalidCommand, TabbyExceptionInvalidInput,
            TabbyExceptionInvalidTaskNumber, TabbyExceptionIncompleteCommand {

        if (Parser.validateInput(input)) {
            throw new TabbyExceptionInvalidCommand();
        }

        try {
            String[] parsedTask = Parser.parseTask(input);
            Command command = Command.valueOf(parsedTask[0].toUpperCase());

            return switch (command) {
                case LIST -> new ListAction();
                case TODO, DEADLINE, EVENT ->
                        new AddAction(parsedTask, isDone, isUserInput, ui);
                case MARK, UNMARK, DELETE -> {
                    if (parsedTask.length < 2) {
                        throw new TabbyExceptionInvalidTaskNumber();
                    }
                    try {
                        int taskNumber = Integer.parseInt(parsedTask[1]) - 1;
                        yield switch (command) {
                            case MARK -> new MarkAction(taskNumber);
                            case UNMARK -> new UnmarkAction(taskNumber);
                            case DELETE -> new DeleteAction(taskNumber);
                            default -> throw new TabbyExceptionInvalidCommand();
                        };
                    } catch (NumberFormatException e) {
                        throw new TabbyExceptionInvalidTaskNumber();
                    }
                }
                default -> throw new TabbyExceptionInvalidCommand();
            };

        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }


    /**
     * Runs the action with the provided task manager.
     *
     * @param taskManager The TaskManager to operate on.
     * @throws TabbyException If an error occurs while executing the action.
     */
    public abstract void runTask(TaskManager taskManager) throws TabbyException;
}
