package action;

import exceptions.*;
import task.TaskManager;

/**
 * Abstract class representing an action that can be performed.
 */
public abstract class Action {

    /**
     * Parses the user input and returns the appropriate Action object.
     *
     * @param userInput The user's input string.
     * @return An Action object representing the parsed command.
     * @throws TabbyExceptionInvalidCommand If the command is invalid.
     * @throws TabbyExceptionInvalidMark If the mark command is invalid.
     * @throws TabbyExceptionInvalidTaskNumber If the task number is not a valid integer.
     */
    public static Action parseUserInput(String userInput) throws TabbyExceptionInvalidCommand,
            TabbyExceptionInvalidMark, TabbyExceptionInvalidTaskNumber {
        userInput = userInput.trim();

        if (userInput == null || userInput.isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }
        String[] tokens = userInput.split(" ", 2);
        String taskAction = tokens[0].toLowerCase();

        switch (taskAction) {
            case "list":
                return new ListAction();
            case "todo":
            case "deadline":
            case "event":
                return new AddAction(userInput);
            case "mark":
            case "unmark":
                if (tokens.length < 2) {
                    throw new TabbyExceptionInvalidMark();
                }
                try {
                    int taskNumber = Integer.parseInt(tokens[1]) - 1;
                    return taskAction.equals("mark") ?
                            new MarkAction(taskNumber) :
                            new UnmarkAction(taskNumber);
                } catch (NumberFormatException e) {
                    throw new TabbyExceptionInvalidTaskNumber();
                }
            default:
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
