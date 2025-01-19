package action;
import task.TaskManager;

/**
 * An action that marks a task as not done.
 */
public class UnmarkAction extends Action {
    private final int taskNumber;

    /**
     * Constructs a UnmarkAction with the specified task number.
     *
     * @param taskNumber The task number to be marked as done.
     */
    public UnmarkAction(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the action to mark a task as done.
     *
     * @param taskManager The TaskManager to operate on.
     */
    @Override
    public void runTask(TaskManager taskManager) {
        taskManager.markTaskNotDone(taskNumber);
    }
}