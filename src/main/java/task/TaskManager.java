package task;

import java.util.ArrayList;

import tabby.Ui;

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
            return "= >_< = Error \n Unable to delete task. " + e.getMessage();
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
            return String.format("= ^_^ = Alrights. I've %s this task:\n %s\nNow you have 1 task in the list",
                    command, task);
        } else {
            return String.format("= ^_^ = Alrights. I've %s this task:\n %s\nNow you have %d tasks in the list",
                    command, task, noOfTasks);
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
            return ui.display("= ^_^ = There are no tasks in your list!");
        } else {
            StringBuilder taskListString = new StringBuilder("= ^_^ = Here are the tasks in your list:\n");
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
            return String.format("= ^_^ = Solid! I've marked this task as done:\n %s", task);
        } else {
            return "= >_< =Error \n Invalid task number.";
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
            return String.format("= ^_^ =\n Okais, I've marked this task as not done yet:\n %s", task);
        } else {
            return "= >_< = Error \n Invalid task number.";
        }
    }

    /**
     * Searches the task list for tasks containing the specified keyword and displays the matching tasks.
     *
     * @param keyword The keyword to search for within task descriptions.
     * @return A message containing the matching tasks or an error message.
     */
    public String findTask(String keyword) {
        if (taskList.isEmpty()) {
            return "= >_< = Error \n There are no tasks in your list!";
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
            return "= >_< = Error \n No matching tasks found!";
        } else {
            return "= ^_^ = Here are the matching tasks in your list:\n" + taskListString.toString().trim();
        }
    }

    /**
     * Generates a reminder for upcoming deadlines and events in the task list.
     *
     * @return A string containing upcoming deadlines and events or an error message if no tasks are found.
     */
    public String remind() {
        if (taskList.isEmpty()) {
            return "= >_< = Error \n There are no tasks in your list!";
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
            return "= >_< =Error \n No matching tasks found!";
        } else {
            return "= ^_^ = Here are upcoming deadlines/events in your list:\n" + taskListString.toString().trim();
        }
    }

}

