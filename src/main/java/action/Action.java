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
        FIND,
        REMINDER
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

        validateInitialInput(input);
        String[] parsedTask = Parser.parseTask(input);
        Command command = parseCommand(parsedTask);

        return createAction(command, parsedTask, isDone, isUserInput, ui);
    }

    private static void validateInitialInput(String input)
            throws TabbyExceptionInvalidCommand {
        if (input == null || input.trim().isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }
        if (Parser.validateInput(input)) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    private static Command parseCommand(String[] parsedTask)
            throws TabbyExceptionInvalidCommand {
        validateParsedTask(parsedTask);
        try {
            return Command.valueOf(parsedTask[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    private static void validateParsedTask(String[] parsedTask)
            throws TabbyExceptionInvalidCommand {
        if (parsedTask == null || parsedTask.length == 0) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    private static Action createAction(Command command, String[] parsedTask,
                                       boolean isDone, boolean isUserInput, Ui ui)
            throws TabbyExceptionIncompleteCommand, TabbyExceptionInvalidTaskNumber,
            TabbyExceptionInvalidCommand {

        try {
            return switch (command) {
                case LIST -> new ListAction();
                case REMINDER -> new RemindAction();
                case FIND -> createFindAction(parsedTask);
                case TODO, DEADLINE, EVENT -> createAddAction(parsedTask, isDone, isUserInput, ui);
                case MARK, UNMARK, DELETE -> createTaskManagementAction(command, parsedTask);
                default -> throw new TabbyExceptionInvalidCommand();
            };
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    private static Action createFindAction(String[] parsedTask)
            throws TabbyExceptionIncompleteCommand {
        if (parsedTask.length < 2) {
            throw new TabbyExceptionIncompleteCommand();
        }

        assert parsedTask.length < 2;

        if (parsedTask[1].trim().isEmpty()) {
            throw new TabbyExceptionIncompleteCommand();
        }
        return new FindAction(parsedTask[1].trim());
    }

    private static Action createAddAction(String[] parsedTask, boolean isDone,
                                          boolean isUserInput, Ui ui) throws TabbyExceptionIncompleteCommand {
        if (parsedTask.length < 2) {
            throw new TabbyExceptionIncompleteCommand();
        }
        assert parsedTask.length < 2;
        return new AddAction(parsedTask, isDone, isUserInput, ui);
    }

    private static Action createTaskManagementAction(Command command, String[] parsedTask)
            throws TabbyExceptionInvalidTaskNumber, TabbyExceptionInvalidCommand {

        try {
            validateTaskNumber(parsedTask);  // Ensure task number exists
            int taskNumber = parseTaskNumber(parsedTask[1]);  // Parse the task number
            validateTaskNumberRange(taskNumber);  // Ensure it's within a valid range

            return switch (command) {
                case MARK -> new MarkAction(taskNumber);
                case UNMARK -> new UnmarkAction(taskNumber);
                case DELETE -> new DeleteAction(taskNumber);
                default -> throw new TabbyExceptionInvalidCommand();
            };
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }


    private static void validateTaskNumber(String[] parsedTask)
            throws TabbyExceptionInvalidTaskNumber {
        if (parsedTask.length < 2) {
            throw new TabbyExceptionInvalidTaskNumber();
        }
        assert parsedTask.length < 2;
        if (parsedTask[1].trim().isEmpty()) {
            throw new TabbyExceptionInvalidTaskNumber();
        }

    }

    private static void validateTaskNumberRange(int taskNumber)
            throws TabbyExceptionInvalidTaskNumber {
        if (taskNumber < 0) {
            throw new TabbyExceptionInvalidTaskNumber();
        }
    }

    private static int parseTaskNumber(String numberStr)
            throws TabbyExceptionInvalidTaskNumber {
        try {
            return Integer.parseInt(numberStr.trim()) - 1;
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