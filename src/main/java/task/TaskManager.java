package task;

import tabby.Ui;

import java.util.ArrayList;


/**
 * Manages a list of tasks, allowing for addition and completion tracking.
 */
public class TaskManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;
    private final Ui ui;

    public TaskManager(Storage storage, Ui ui) {
        this.storage = storage;
        this.taskList = new ArrayList<>();
        this.ui = ui;
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
        try {
            // Check if the task number is within a valid range
            if (taskNumber < 0 || taskNumber >= taskList.size()) {
                throw new IndexOutOfBoundsException("Task number is out of range.");
            }

            // Retrieve and delete the task
            Task task = taskList.get(taskNumber);
            taskList.remove(taskNumber);

            // Provide feedback and save the updated list
            taskResponse("deleted", task);
            storage.saveTasks(taskList);

        } catch (IndexOutOfBoundsException e) {
            ui.error("Error: Unable to delete task. " + e.getMessage());
        }
    }

    public void taskResponse(String command, Task task) {
        int noOfTasks = taskList.size();
        if (noOfTasks == 1) {
           ui.display(String.format("Got it. I've %s this task:" +
                    "\n %s\nNow you have 1 task in the list",command,task));
        } else {
            ui.display(String.format("Got it. I've %s this task:" +
                    "\n %s\nNow you have %d tasks in the list", command, task, noOfTasks));
        }
    }

    public ArrayList<Task> getTasks() {
        return taskList;
    }

    /**
     * Displays the list of tasks.
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
     * Marks a task as done.
     * @param taskNumber The index of the task to mark as done.
     */
    public void markTaskDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsDone();
            ui.display(String.format("Nice! I've marked this task as done:\n %s",task));
        } else {
            ui.error("Invalid task number.");
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
            ui.display(String.format("OK, I've marked this task as not done yet:\n %s",task));
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
