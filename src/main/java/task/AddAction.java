package task;
/**
 * An action that marks a task as done.
 */
public class AddAction extends Action {
    private final String description;

    /**
     * Constructs a AddAction with the specified description
     *
     * @param description The task number to be marked as done.
     */
    public AddAction(String description) {
        this.description = description;
    }

    /**
     * Executes the action to add action to a list
     *
     * @param taskManager The TaskManager to operate on.
     * @return A message indicating the task has been added
     */

    @Override
    public void runTask(TaskManager taskManager) {
        taskManager.addTask(description);
    }
}