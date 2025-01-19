package action;

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
     */
    public static Action parseUserInput(String userInput) {
        userInput = userInput.trim();
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
                return new MarkAction(Integer.parseInt(tokens[1]) - 1);
            case "unmark":
                return new UnmarkAction(Integer.parseInt(tokens[1]) - 1);
            default:
                return null;
        }
    }

    /**
     * Runs the action with the provided task manager.
     *
     * @param taskManager The TaskManager to operate on.
     */
    public abstract void runTask(TaskManager taskManager);
}
