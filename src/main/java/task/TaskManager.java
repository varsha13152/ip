package task;

import java.util.ArrayList;

/**
 * Manages a list of tasks, allowing for addition and completion tracking.
 */
public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<Task>();

    /**
     * Adds a new task to the task list.
     * @param task The task to be added
     */
    public void addTask(Task task) {
        taskList.add(task);
        System.out.println(String.format("Got it. I've added this task:" +
                "\n %s\nNow you have %d tasks in the list",task,taskList.size()));
    }

    /**
     * Displays the list of tasks.
     */
    public void displayTaskList() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks in your list!");
            return;
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
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    /**
     * Marks a task as not done.
     * @param taskNumber The index of the task to mark as not done.
     */
    public void markTaskNotDone(int taskNumber) {
        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);
            task.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(task);
        } else {
            System.out.println("Invalid task number.");
        }
    }
}
