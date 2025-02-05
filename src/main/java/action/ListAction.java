package action;
import task.TaskManager;
/**
 * Represents the Action on a List
 */
public class ListAction extends Action {

    public ListAction() { }

    /**
     * Executes the action to display all tasks in a list
     *
     * @param taskManager The TaskManager to operate on.
     */
    @Override
    public String runTask(TaskManager taskManager) {
        return taskManager.displayTaskList();
    }
}