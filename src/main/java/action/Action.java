package action;

import exceptions.*;
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
        EVENT
    }
    /**
     * Parses the user input and returns the appropriate Action object.
     *
     * @param userInput The user's input string.
     * @return An Action object representing the parsed command.
     * @throws TabbyExceptionInvalidCommand If the command is invalid.
     * @throws TabbyExceptionInvalidInput If the mark command is invalid.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     */
    public static Action parseUserInput(String userInput) throws TabbyExceptionInvalidCommand,
            TabbyExceptionInvalidInput, TabbyExceptionInvalidTaskNumber {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }
        userInput = userInput.trim();
        String[] tokens = userInput.split(" ", 2);
        String taskAction = tokens[0].toUpperCase();

        try {
             Command command = Command.valueOf(taskAction);
            switch (command) {
                case LIST:
                    return new ListAction();
                case TODO:
                case DEADLINE:
                case EVENT:
                    return new AddAction(userInput);
                case MARK:
                case UNMARK:
                case DELETE:
                    try {
                        int taskNumber = Integer.parseInt(tokens[1]) - 1;
                        if (command == Command.MARK) {
                            return new MarkAction(taskNumber);
                        } else if (command == Command.UNMARK) {
                            return new UnmarkAction(taskNumber);
                        } else {
                            return new DeleteAction(taskNumber);
                        }
                    } catch (NumberFormatException e) {
                        throw new TabbyExceptionInvalidTaskNumber();
                    }
                default:
                    throw new TabbyExceptionInvalidCommand();
            }
        } catch(IllegalArgumentException e) {
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
