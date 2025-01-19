package action;
import task.TaskManager;
import task.ToDo;
import task.Event;
import task.Deadline;

/**
 * Handles adding new tasks to the task list.
 * This class processes user input to create appropriate task types.
 */
public class AddAction extends Action {
    private static final String DEADLINE_DELIMITER = "/by";
    private static final String EVENT_FROM_DELIMITER = "/from";
    private static final String EVENT_TO_DELIMITER = "/to";

    private final String userInput;

    /**
     * Constructs an AddAction with the specified user input.
     *
     * @param userInput The task input string to be processed.
     */
    public AddAction(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Executes the action to add a task to the list.
     *
     * @param taskManager The TaskManager to operate on.
     * @return A message indicating the task has been added.
     */
    @Override
    public void runTask(TaskManager taskManager) {
        String[] userInputTokens = userInput.split(" ", 2);

        if (userInputTokens.length < 2) {
            throw new IllegalArgumentException("Invalid input format. Expected: <taskType> <description>");
        }

        String taskType = userInputTokens[0].trim();
        String taskDescription = userInputTokens[1].trim();

        switch (taskType) {
            case "todo":
                addTodoTask(taskManager, taskDescription);
                break;
            case "deadline":
                addDeadlineTask(taskManager, taskDescription);
                break;
            case "event":
                addEventTask(taskManager, taskDescription);
                break;
            default:
                throw new IllegalArgumentException("Invalid task type: " + taskType);
        }
    }

    private void addTodoTask(TaskManager taskManager, String description) {
        taskManager.addTask(new ToDo(description.trim()));
    }

    private void addDeadlineTask(TaskManager taskManager, String description) {
        String[] tokens = description.split(DEADLINE_DELIMITER, 2);
        taskManager.addTask(new Deadline(tokens[0].trim(), tokens[1].trim()));
    }

    private void addEventTask(TaskManager taskManager, String description) {
        String[] firstSplit = description.split(EVENT_FROM_DELIMITER, 2);
        String[] secondSplit = firstSplit[1].split(EVENT_TO_DELIMITER, 2);
        taskManager.addTask(new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim()));
    }
}
