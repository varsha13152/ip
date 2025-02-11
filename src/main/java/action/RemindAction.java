package action;
import task.TaskManager;
/**
 * An action that reminds user about upcoming tasks which are not done yet
 */

public class RemindAction extends Action {

    /**
     * Executes the action to mark a task as done.
     *
     * @param taskManager The TaskManager to operate on.
     */
    @Override
    public String runTask(TaskManager taskManager) {
        return taskManager.remind();
    }
}
