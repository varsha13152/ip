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

    /**
     * Validates the initial user input before processing.
     *
     * @param input The user-provided input string to be validated.
     * @throws TabbyExceptionInvalidCommand If the input is null, empty, or fails validation.
     */
    private static void validateInitialInput(String input)
            throws TabbyExceptionInvalidCommand {
        if (input == null || input.trim().isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }
        if (Parser.validateInput(input)) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    /**
     * Parses the command from the given array of parsed task components.
     *
     * @param parsedTask An array of strings representing the parsed user input.
     * @return A {@code Command} enum value corresponding to the parsed command.
     * @throws TabbyExceptionInvalidCommand If the parsed task array is invalid or if the
     *                                      command is not recognized.
     */
    private static Command parseCommand(String[] parsedTask)
            throws TabbyExceptionInvalidCommand {
        validateParsedTask(parsedTask);
        try {
            return Command.valueOf(parsedTask[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    /**
     * Validates the parsed task array to ensure it is not null or empty.
     *
     * @param parsedTask The array of parsed task components to be validated.
     * @throws TabbyExceptionInvalidCommand If the {@code parsedTask} array is {@code null} or empty.
     */
    private static void validateParsedTask(String[] parsedTask)
            throws TabbyExceptionInvalidCommand {
        if (parsedTask == null || parsedTask.length == 0) {
            throw new TabbyExceptionInvalidCommand();
        }
    }

    /**
     * Creates an appropriate {@code Action} object based on the parsed command.
     *
     * @param command The {@code Command} enum representing the user command.
     * @param parsedTask An array of strings containing the parsed task details.
     * @param isDone A boolean flag indicating whether the task should be marked as done.
     * @param isUserInput A boolean flag indicating whether the input originated from a user.
     * @param ui The UI handler responsible for user feedback.
     * @return An {@code Action} object corresponding to the given command.
     * @throws TabbyExceptionIncompleteCommand If the command is missing required details.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     * @throws TabbyExceptionInvalidCommand If the command is unrecognized or invalid.
     */
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

    /**
     * Creates a {@code FindAction} based on the parsed task input.
     *
     * @param parsedTask An array of strings containing the parsed user input.
     *                   The second element is expected to be the search query.
     * @return A {@code FindAction} object initialized with the search query.
     * @throws TabbyExceptionIncompleteCommand If the search query is missing or empty.
     */
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

    /**
     * Creates an {@code AddAction} based on the parsed task input.
     *
     * @param parsedTask An array of strings representing the parsed user input.
     *                   The second element is expected to contain task details.
     * @param isDone A boolean flag indicating whether the task should be marked as done.
     * @param isUserInput A boolean flag indicating whether the input originated from a user.
     * @param ui The UI handler responsible for user feedback.
     * @return An {@code AddAction} object initialized with the given task details.
     * @throws TabbyExceptionIncompleteCommand If the parsed task input is incomplete.
     */
    private static Action createAddAction(String[] parsedTask, boolean isDone,
                                          boolean isUserInput, Ui ui) throws TabbyExceptionIncompleteCommand {
        if (parsedTask.length < 2) {
            throw new TabbyExceptionIncompleteCommand();
        }
        assert parsedTask.length < 2;
        return new AddAction(parsedTask, isDone, isUserInput, ui);
    }

    /**
     * Creates an appropriate task {@code Action} based on the provided command.
     *
     * @param command The {@code Command} enum representing the user command (MARK, UNMARK, DELETE).
     * @param parsedTask An array of strings containing the parsed user input.
     *                   The second element is expected to be the task number.
     * @return An {@code Action} object corresponding to the given command.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is invalid or out of range.
     * @throws TabbyExceptionInvalidCommand If the command is not recognized.
     */
    private static Action createTaskManagementAction(Command command, String[] parsedTask)
            throws TabbyExceptionInvalidTaskNumber, TabbyExceptionInvalidCommand {

        try {
            validateTaskNumber(parsedTask);
            int taskNumber = parseTaskNumber(parsedTask[1]);
            validateTaskNumberRange(taskNumber);

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

    /**
     * Validates the task number in the parsed task array.
     * @param parsedTask An array of strings representing the parsed user input.
     *                   The second element is expected to be the task number.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is missing or empty.
     */
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

    /**
     * Validates that the task number is within an acceptable range.
     *
     * @param taskNumber The task number to be validated.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is negative.
     */
    private static void validateTaskNumberRange(int taskNumber)
            throws TabbyExceptionInvalidTaskNumber {
        if (taskNumber < 0) {
            throw new TabbyExceptionInvalidTaskNumber();
        }
    }

    /**
     * Parses the given string into a task number.
     *
     * @param numberStr The string representing the task number.
     * @return The parsed task number, adjusted to be zero-based.
     * @throws TabbyExceptionInvalidTaskNumber If the string is not a valid integer.
     */
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
