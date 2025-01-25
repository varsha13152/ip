package task;


import java.util.ArrayList;


/**
 * Manages a list of tasks, allowing for addition and completion tracking.
 */
public class TaskManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    public TaskManager(Storage storage) {
        this.storage = storage;
        this.taskList = new ArrayList<>();
        loadTasks();
    }

    public ArrayList<Task> loadTasks() {
        return storage.loadTasks(this);
    }

    /**
     * Adds a new task to the task list.
     * @param task The task to be added
     */
    public void addTask(Task task) {
        taskList.add(task);
        storage.saveTasks(taskList);
    }

    public void deleteTask(int taskNumber) {
        Task task = taskList.get(taskNumber);
        taskList.remove(taskNumber);
        taskResponse("deleted",task);
        storage.saveTasks(taskList);
    }

    public void taskResponse(String command, Task task) {
        int noOfTasks = taskList.size();
        if (noOfTasks == 1) {
            System.out.println(String.format("Got it. I've %s this task:" +
                    "\n %s\nNow you have 1 task in the list",command,task));
        } else {
            System.out.println(String.format("Got it. I've %s this task:" +
                    "\n %s\nNow you have %d tasks in the list", command, task, noOfTasks));
        }
    }

    /**
     * Displays the list of tasks.
     */
    public void displayTaskList() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks in your list!");
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }

    /**
     * Marks a task as done.
     * @param taskNumber The index of the task to mark as done.
     */
    public void markTaskDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsDone();
            System.out.println(String.format("Nice! I've marked this task as done:\n %s",task));
        } else {
            System.out.println("Invalid task number.");
        }
        storage.saveTasks(taskList);
    }

    /**
     * Marks a task as not done.
     * @param taskNumber The index of the task to mark as not done.
     */
    public void markTaskNotDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsNotDone();
            System.out.println(String.format("OK, I've marked this task as not done yet:\n %s",task));
        } else {
            System.out.println("Invalid task number.");
        }
        storage.saveTasks(taskList);
    }
}
