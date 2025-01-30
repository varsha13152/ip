package task;

import tabby.Ui;

import java.util.ArrayList;

/**
 * Manages a list of tasks, providing functionality to add, delete, mark as done, and display tasks.
 * Also integrates with the Storage class for saving and loading tasks.
 */
public class TaskManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;
    private final Ui ui;

    /**
     * Constructs a TaskManager instance with the specified storage and user interface.
     *
     * @param storage The Storage instance for saving and loading tasks.
     * @param ui      The Ui instance for displaying messages and errors.
     */
    public TaskManager(Storage storage, Ui ui) {
        this.storage = storage;
        this.taskList = new ArrayList<>();
        this.ui = ui;
        loadTasks();
    }

    /**
     * Loads tasks from storage into the task list.
     *
     * @return An ArrayList of loaded tasks.
     */
    public ArrayList<Task> loadTasks() {
        return storage.loadTasks(this);
    }

    /**
     * Adds a new task to the task list and saves it to storage.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        taskList.add(task);
        storage.saveTasks(taskList);
    }

    /**
     * Deletes a task from the task list based on its index and saves the updated list to storage.
     *
     * @param taskNumber The index of the task to be deleted.
     */
    public void deleteTask(int taskNumber) {
        try {
            if (taskNumber < 0 || taskNumber >= taskList.size()) {
                throw new IndexOutOfBoundsException("Task number is out of range.");
            }

            // Retrieve and delete the task
            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);

            // Provide feedback and save updated list
            taskResponse("deleted", task);
            storage.saveTasks(taskList);

        } catch (IndexOutOfBoundsException e) {
            ui.error("Error: Unable to delete task. " + e.getMessage());
        }
    }

    /**
     * Provides feedback to the user about a task-related action.
     *
     * @param command The action performed (e.g., "added", "deleted").
     * @param task    The task that was affected.
     */
    public void taskResponse(String command, Task task) {
        int noOfTasks = taskList.size();
        if (noOfTasks == 1) {
            ui.display(String.format("Got it. I've %s this task:\n %s\nNow you have 1 task in the list", command, task));
        } else {
            ui.display(String.format("Got it. I've %s this task:\n %s\nNow you have %d tasks in the list", command, task, noOfTasks));
        }
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return An ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return taskList;
    }

    /**
     * Displays all tasks in the task list to the user.
     */
    public void displayTaskList() {
        if (taskList.isEmpty()) {
            ui.display("No tasks in your list!");
        } else {
            StringBuilder taskListString = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < taskList.size(); i++) {
                taskListString.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
            }
            // Feed the combined string to ui.display
            ui.display(taskListString.toString().trim());
        }
    }

    /**
     * Marks a task as done based on its index and saves the updated list to storage.
     *
     * @param taskNumber The index of the task to mark as done.
     */
    public void markTaskDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsDone();
            ui.display(String.format("Nice! I've marked this task as done:\n %s", task));
        } else {
            ui.error("Invalid task number.");
        }
        storage.saveTasks(taskList);
    }

    /**
     * Marks a task as not done based on its index and saves the updated list to storage.
     *
     * @param taskNumber The index of the task to mark as not done.
     */
    public void markTaskNotDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsNotDone();
            ui.display(String.format("OK, I've marked this task as not done yet:\n %s", task));
        } else {
            ui.error("Invalid task number.");
        }
        storage.saveTasks(taskList);
    }

    public void findTask(String keyword) {
        if (taskList.isEmpty()) {
            ui.error("There are no tasks in your list!");
        } else {
            StringBuilder taskListString = new StringBuilder();
            int matchCount = 0;

            for (int i = 0; i < taskList.size(); i++) {
                String task = taskList.get(i).toString();
                if (task.contains(keyword)) {
                    taskListString.append(++matchCount).append(". ").append(task).append("\n");
                }
            }
            if (matchCount == 0) {
                ui.error("No matching tasks found!");
            } else {
                ui.display("Here are the matching tasks in your list:\n" + taskListString.toString().trim());
            }
        }
    }

}

