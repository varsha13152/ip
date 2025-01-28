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
import tabby.Ui;

public class Storage {
    public final String directory;
    public final String fileName;
    private final Ui ui;

    public Storage(String directory, String fileName, Ui ui) {
        this.directory = directory;
        this.fileName = fileName;
        this.ui = ui;
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
                ui.error("Error creating file: " + taskFile.getAbsolutePath());
            }
            return taskList;
        }

        try (Scanner scanner = new Scanner(taskFile)) {
            while (scanner.hasNext()) {
                String data = scanner.nextLine().trim();
                if (!Parser.validateInput(data)) {
                    HashMap<String, String> taskDetails = Parser.parseFileRead(data);
                    Action action = new AddAction(new String[]{taskDetails.get("task"), taskDetails.get("description")},
                            Boolean.parseBoolean(taskDetails.get("status")),false,ui);
                    action.runTask(taskManager);

                }
            }
        } catch (FileNotFoundException e) {
            ui.error("File not found - " + taskFile.getAbsolutePath());
        } catch (TabbyException e) {
            ui.error("Unable to process task file: " + e.getMessage());
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
            ui.error("Error writing to file: " + taskFile.getAbsolutePath());
        }
    }

}
