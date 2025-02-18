package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import action.Action;
import action.AddAction;
import action.Parser;
import exceptions.TabbyException;
import tabby.Ui;

/**
 * The Storage class is responsible for managing file I/O operations related to task storage.
 * It handles loading tasks from a file and saving tasks to a file.
 */
public class Storage {
    public final String directory;
    public final String fileName;
    private final Ui ui;

    /**
     * Constructs a Storage object to manage task files.
     *
     * @param directory The directory where the task file is stored.
     * @param fileName  The name of the file that contains the tasks.
     * @param ui        The Ui instance used to display messages and errors.
     */
    public Storage(String directory, String fileName, Ui ui) {
        this.directory = directory;
        this.fileName = fileName;
        this.ui = ui;
    }

    /**
     * Loads tasks from the task file into the provided TaskManager.
     *
     * @param taskManager The TaskManager instance to which the tasks will be added.
     * @return An ArrayList of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks(TaskManager taskManager) {
        ArrayList<Task> taskList = new ArrayList<>();
        File folder = new File(directory);

        // Ensure the directory exists
        if (!folder.exists()) {
            folder.mkdir();
        }

        File taskFile = new File(String.format("%s/%s", directory, fileName));

        // Create the task file if it doesn't exist
        if (!taskFile.exists()) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                ui.error("Error creating file: " + taskFile.getAbsolutePath());
            }
            return taskList;
        }

        // Read tasks from the file
        try (Scanner scanner = new Scanner(taskFile)) {
            while (scanner.hasNext()) {
                String data = scanner.nextLine().trim();
                if (!Parser.validateInput(data)) {
                    HashMap<String, String> taskDetails = Parser.parseFileRead(data);
                    Action action = new AddAction(
                            new String[]{taskDetails.get("task"), taskDetails.get("description")},
                            Boolean.parseBoolean(taskDetails.get("status")),
                            false,
                            ui
                    );
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

    /**
     * Saves the list of tasks to the task file.
     *
     * @param taskList The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> taskList) {
        File folder = new File(this.directory);

        // Ensure the directory exists
        if (!folder.exists()) {
            folder.mkdir();
        }

        File taskFile = new File(folder, this.fileName);

        // Write tasks to the file
        try (FileWriter writer = new FileWriter(taskFile)) {
            for (Task task : taskList) {
                writer.write(task.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            ui.error("Error writing to file: " + taskFile.getAbsolutePath());
        }
    }
}
