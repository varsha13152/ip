package task;

import action.Action;
import action.AddAction;
import action.Parser;
import exceptions.TabbyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Storage {
    public final String directory;
    public final String fileName;

    public Storage(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    public ArrayList<Task> loadTasks(TaskManager taskManager) {
        ArrayList<Task> taskList = new ArrayList<>();
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

        try (Scanner scanner = new Scanner(taskFile)) {
            while (scanner.hasNext()) {
                String data = scanner.nextLine().trim();
                if (!Parser.validateInput(data)) {
                    HashMap<String, String> taskDetails = Parser.parseFileRead(data);
                    Action action = new AddAction(new String[]{taskDetails.get("task"), taskDetails.get("description")},
                            Boolean.parseBoolean(taskDetails.get("status")),false);
                    action.runTask(taskManager);

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + taskFile.getAbsolutePath());
        } catch (TabbyException e) {
            System.out.println("Error processing task file: " + e.getMessage());
        }

        return taskList;
    }


    public void saveTasks(ArrayList<Task> taskList) {
        File folder = new File(this.directory);

        if (!folder.exists()) {
            folder.mkdir();
        }

        File taskFile = new File(folder, this.fileName);

        try (FileWriter writer = new FileWriter(taskFile)) {
            for (Task task : taskList) {
                writer.write(task.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + taskFile.getAbsolutePath());
        }
    }

}
