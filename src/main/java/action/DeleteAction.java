package action;
import task.TaskManager;
/**
 * An action that deletes a task
 */
public class DeleteAction extends Action {
    private final int taskNumber;

    /**
     * Constructs a DeleteAction with the specified task number.
     *
     * @param taskNumber The task number to be deleted.
     */
    public DeleteAction(int taskNumber) {

        this.taskNumber = taskNumber;
    }

    /**
     * Executes the action to delete task
     *
     * @param taskManager The TaskManager to operate on.
     */
    @Override
    public String runTask(TaskManager taskManager) {
        return taskManager.deleteTask(taskNumber);
    }
}
