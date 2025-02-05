package action;
import task.TaskManager;
/**
 * An action that finds tasks
 */
public class FindAction extends Action {
    private final String keyword;

    /**
     * Constructs a FindAction with the specified keyword.
     *
     * @param keyword The keyword to be match tasks
     */
    public FindAction(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the action to mark a task as done.
     *
     * @param taskManager The TaskManager to operate on.
     */
    @Override
    public String runTask(TaskManager taskManager) {
        return taskManager.findTask(keyword);
    }
}
