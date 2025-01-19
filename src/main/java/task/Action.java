package task;

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
        if (userInput == null || userInput.trim().isEmpty()) {
            return null;
        }

        userInput = userInput.trim().toLowerCase();
        String[] tokens = userInput.split(" ", 2);
        String taskAction = tokens[0];

        if (taskAction.equalsIgnoreCase("list")) {
            return new ListAction();
        }

        if (taskAction.equalsIgnoreCase("add")) {
            if (tokens.length < 2) {
                System.err.println("Error: Task description required for 'add' command.");
                return null;
            }
            String taskDescription = tokens[1];  // Get full task description
            return new AddAction(taskDescription);
        }

        if (tokens.length < 2) {
            return null;
        }



        int taskNumber = Integer.parseInt(tokens[1]) - 1;  // Convert to zero-based index
        if (taskAction.equalsIgnoreCase("mark")) {
            return new MarkAction(taskNumber);
        } else if (taskAction.equalsIgnoreCase("unmark")) {
            return new UnmarkAction(taskNumber);
        }
        return null;
    }

    /**
     * Runs the action with the provided task manager.
     *
     * @param taskManager The TaskManager to operate on.
     */
    public abstract void runTask(TaskManager taskManager);
}
