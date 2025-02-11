package action;

import exceptions.TabbyException;
import exceptions.TabbyExceptionInvalidCommand;
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
     * Parses user input and returns an appropriate Action based on the specified command.
     *
     * @param input       The user input containing the command and its parameters.
     * @param isDone      A flag indicating whether the task is already marked as done.
     * @param isUserInput A flag indicating whether the input is from the user or a file.
     * @param ui          The UI object for interaction and displaying output.
     * @return The corresponding Action object based on the user input command.
     * @throws TabbyExceptionInvalidCommand     If the input is invalid or the command is not recognized.
     * @throws TabbyExceptionInvalidTaskNumber  If the task number is missing or not a valid number.
     */
    public static Action userAction(String input, boolean isDone, boolean isUserInput, Ui ui)
            throws TabbyExceptionInvalidCommand, TabbyExceptionInvalidTaskNumber {

        if (Parser.validateInput(input)) {
            throw new TabbyExceptionInvalidCommand();
        }


        String[] parsedTask = Parser.parseTask(input);
        assert parsedTask.length > 0;

        Command command = parseCommand(parsedTask[0]);

        return switch (command) {
        case LIST -> new ListAction();
        case FIND -> {
            if (parsedTask.length < 2) {
                throw new TabbyExceptionInvalidCommand();
            }
            yield new FindAction(parsedTask[1]);
        }
        case TODO, DEADLINE, EVENT -> new AddAction(parsedTask, isDone, isUserInput, ui);
        case MARK, UNMARK, DELETE -> handleTaskModification(command, parsedTask);
        default -> throw new TabbyExceptionInvalidCommand();
        };
    }

    /**
     * Parses the given command string and returns the corresponding Command enum value.
     *
     * @param commandString The command string to be parsed.
     * @return The corresponding Command enum value.
     * @throws TabbyExceptionInvalidCommand If the command string does not match any valid command.
     */
    private static Command parseCommand(String commandString) throws TabbyExceptionInvalidCommand {
        try {
            return Command.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    /**
     * Handles task modification commands such as MARK, UNMARK, and DELETE.
     *
     * @param command    The command specifying the action to perform.
     * @param parsedTask The parsed task array containing the task number.
     * @return The corresponding Action object for the task modification.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is missing or invalid.
     * @throws TabbyExceptionInvalidCommand    If the command is not a valid task modification command.
     */
    private static Action handleTaskModification(Command command, String[] parsedTask)
            throws TabbyExceptionInvalidTaskNumber, TabbyExceptionInvalidCommand {

        if (parsedTask.length < 2) {
            throw new TabbyExceptionInvalidTaskNumber();
        }

        int taskNumber = parseTaskNumber(parsedTask[1]);

        return switch (command) {
        case MARK -> new MarkAction(taskNumber);
        case UNMARK -> new UnmarkAction(taskNumber);
        case DELETE -> new DeleteAction(taskNumber);
        default -> throw new TabbyExceptionInvalidCommand();
        };
    }

    /**
     * Parses and validates the task number from the given string.
     *
     * @param taskNumberStr The string representation of the task number.
     * @return The parsed task number as an integer (0-based index).
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     */
    private static int parseTaskNumber(String taskNumberStr) throws TabbyExceptionInvalidTaskNumber {
        try {
            return Integer.parseInt(taskNumberStr) - 1;
        } catch (NumberFormatException e) {
            throw new TabbyExceptionInvalidTaskNumber();
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
