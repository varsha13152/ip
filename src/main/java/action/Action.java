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
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }
        userInput = userInput.trim();
        String[] tokens = userInput.split(" ", 2);
        String taskAction = tokens[0].toLowerCase();

        switch (taskAction) {
            case "list":
                return new ListAction();
            case "todo":
            case "deadline":
            case "event":
                if (tokens.length < 2) {
                    throw new TabbyExceptionInvalidCommand();
                }
                return new AddAction(userInput);
            case "mark":
            case "unmark":
            case "remove":
                if (tokens.length < 2) {
                    throw new TabbyExceptionInvalidMark();
                }
                try {
                    int taskNumber = Integer.parseInt(tokens[1]) - 1;
                    if (taskAction.equals("mark")) {
                        return new MarkAction(taskNumber);
                    } else if (taskAction.equals("unmark")) {
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
    }


    /**
     * Runs the action with the provided task manager.
     *
     * @param taskManager The TaskManager to operate on.
     * @throws TabbyException If an error occurs while executing the action.
     */
    public abstract void runTask(TaskManager taskManager) throws TabbyException;
}
