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
     * @return A message confirming the task deletion or an error message.
     */
    public String deleteTask(int taskNumber) {
        try {
            if (taskNumber < 0 || taskNumber >= taskList.size()) {
                throw new IndexOutOfBoundsException("Task number is out of range.");
            }

            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);
            storage.saveTasks(taskList);
            return taskResponse("deleted", task);
        } catch (IndexOutOfBoundsException e) {
            return "Error: Unable to delete task. " + e.getMessage();
        }
    }


    /**
     * Provides feedback to the user about a task-related action.
     *
     * @param command The action performed (e.g., "added", "deleted").
     * @param task    The task that was affected.
     * @return A message describing the result of the action.
     */
    public String taskResponse(String command, Task task) {
        int noOfTasks = taskList.size();
        if (noOfTasks == 1) {
            return String.format("Got it. I've %s this task:\n %s\nNow you have 1 task in the list", command, task);
        } else {
            return String.format("Got it. I've %s this task:\n %s\nNow you have %d tasks in the list", command, task, noOfTasks);
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
    public String displayTaskList() {
        if (taskList.isEmpty()) {
            return ui.display("No tasks in your list!");
        } else {
            StringBuilder taskListString = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < taskList.size(); i++) {
                taskListString.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
            }
            // Feed the combined string to ui.display
            return taskListString.toString().trim();
        }
    }

    /**
     * Marks a task as done based on its index and saves the updated list to storage.
     *
     * @param taskNumber The index of the task to mark as done.
     * @return A message confirming the task has been marked as done or an error message.
     */
    public String markTaskDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsDone();
            storage.saveTasks(taskList);
            return String.format("Nice! I've marked this task as done:\n %s", task);
        } else {
            return "Invalid task number.";
        }
    }

    /**
     * Marks a task as not done based on its index and saves the updated list to storage.
     *
     * @param taskNumber The index of the task to mark as not done.
     * @return A message confirming the task has been marked as not done or an error message.
     */
    public String markTaskNotDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsNotDone();
            storage.saveTasks(taskList);
            return String.format("OK, I've marked this task as not done yet:\n %s", task);
        } else {
            return "Invalid task number.";
        }
    }

    /**
     * Searches the task list for tasks containing the specified keyword and displays the matching tasks.
     *
     * @param keyword The keyword to search for within task descriptions.
     *
     * If the task list is empty, an error message is displayed indicating there are no tasks.
     * If no matching tasks are found, an error message is displayed.
     * Otherwise, the matching tasks are displayed in a formatted list.
     */
    /**
     * Searches the task list for tasks containing the specified keyword and displays the matching tasks.
     *
     * @param keyword The keyword to search for within task descriptions.
     * @return A message containing the matching tasks or an error message.
     */
    public String findTask(String keyword) {
        if (taskList.isEmpty()) {
            return "There are no tasks in your list!";
        }

        StringBuilder taskListString = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < taskList.size(); i++) {
            String task = taskList.get(i).toString();
            if (task.contains(keyword)) {
                taskListString.append(++matchCount).append(". ").append(task).append("\n");
            }
        }

        if (matchCount == 0) {
            return "No matching tasks found!";
        } else {
            return "Here are the matching tasks in your list:\n" + taskListString.toString().trim();
        }
    }

    public String remind() {
        if (taskList.isEmpty()) {
            return "There are no tasks in your list!";
        }

        StringBuilder taskListString = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (!task.getStatusIcon().equals("X") && !(task instanceof ToDo)) {
                taskListString.append(++matchCount).append(". ").append(task).append("\n");
            }
        }

        if (matchCount == 0) {
            return "No matching tasks found!";
        } else {
            return "Here are upcoming deadlines/events in your list:\n" + taskListString.toString().trim();
        }
    }

}

