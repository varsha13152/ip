package action;

import exceptions.TabbyException;
import exceptions.TabbyExceptionIncompleteCommand;
import exceptions.TabbyExceptionInvalidCommand;
import exceptions.TabbyExceptionInvalidInput;
import exceptions.TabbyExceptionInvalidTaskNumber;
import tabby.Ui;
import task.TaskManager;

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
        EVENT,
        FIND
    }

    /**
     * Parses the user input and returns the appropriate Action object.
     *
     * @param input The user's input string.
     * @param isDone Whether the task is marked as done.
     * @param isUserInput Whether the input comes from a user.
     * @param ui The UI handler for user feedback.
     * @return An Action object representing the parsed command.
     * @throws TabbyExceptionInvalidCommand If the command is invalid.
     * @throws TabbyExceptionInvalidInput If the mark command is invalid.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     * @throws TabbyExceptionIncompleteCommand If the command is incomplete.
     */
    public static Action userAction(String input, boolean isDone, boolean isUserInput, Ui ui)
            throws TabbyExceptionInvalidCommand, TabbyExceptionInvalidInput,
            TabbyExceptionInvalidTaskNumber, TabbyExceptionIncompleteCommand {

        if (Parser.validateInput(input)) {
            throw new TabbyExceptionInvalidCommand();
        }

        try {
            String[] parsedTask = Parser.parseTask(input);
            assert parsedTask.length > 0;
            Command command = Command.valueOf(parsedTask[0].toUpperCase());

            return switch (command) {
            case LIST -> new ListAction();
            case FIND -> {
                if (parsedTask.length < 2) {
                    throw new TabbyExceptionIncompleteCommand();
                }
                yield new FindAction(parsedTask[1]);
            }
            case TODO, DEADLINE, EVENT -> new AddAction(parsedTask, isDone, isUserInput, ui);
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
    public abstract String runTask(TaskManager taskManager) throws TabbyException;
}
