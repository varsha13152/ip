package task;

import java.io.*;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Scanner;

import action.AddAction;
import action.Action;
import exceptions.TabbyException;
import action.Parser;

/**
 * Manages a list of tasks, allowing for addition and completion tracking.
 */
public class TaskManager {
    private final String directory;
    private final String fileName;
    private final ArrayList<Task> taskList;

    public TaskManager(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        this.taskList =  new ArrayList<>();
        loadTasks();
    }



    public ArrayList<Task> loadTasks() {
        File folder = new File(directory);

        if (!folder.exists()) {
            folder.mkdir();
        }

        File taskFile = new File(String.format("%s/%s", directory, fileName));

        if (!taskFile.exists()) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + taskFile.getAbsolutePath());
            }
            return taskList;
        }

        // Read and process tasks from the file
        try (Scanner scanner = new Scanner(taskFile)) {
            while (scanner.hasNext()) {
                String data = scanner.nextLine().trim();
                if (!Parser.validateInput(data)) {
                    HashMap<String, String> taskDetails = Parser.parseFileRead(data);
                    Action action = new AddAction(new String[]{taskDetails.get("task"), taskDetails.get("description")},
                            Boolean.parseBoolean(taskDetails.get("status")),false);
                            action.runTask(this);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + taskFile.getAbsolutePath());
        } catch (TabbyException e) {
            System.out.println("Error processing task file: " + e.getMessage());
        }

        return taskList;
    }


    public void saveTasks() {
        File folder = new File(directory);

        if (!folder.exists()) {
          folder.mkdir();
        }

        File taskFile = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(taskFile)) {
            for (Task task : this.taskList) {
                writer.write(task.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + taskFile.getAbsolutePath());
        }
    }


    /**
     * Adds a new task to the task list.
     * @param task The task to be added
     */
    public void addTask(Task task) {
        taskList.add(task);
        saveTasks();
    }

    public void deleteTask(int taskNumber) {
        Task task = taskList.get(taskNumber);
        taskList.remove(taskNumber);
        taskResponse("deleted",task);
        saveTasks();
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
            System.out.println(String.format("Nice! I've marked this task as done:\n %s",task));
        } else {
            System.out.println("Invalid task number.");
        }
        saveTasks();
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
        saveTasks();
    }
}
