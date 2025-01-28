package action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import exceptions.TabbyException;
import org.junit.jupiter.api.Test;
import tabby.Ui;
import task.Storage;
import task.TaskManager;
import task.Task;

import java.util.ArrayList;

public class AddActionTest {
    private static final String DIRECTORY = "./data";
    private static final String FILENAME = "tabby_data.txt";

    @Test
    public void addToDoTest() {
        Ui ui = new Ui();

        Storage storage = new Storage(DIRECTORY, FILENAME, ui);
        TaskManager taskManager = new TaskManager(storage, ui);
        taskManager.getTasks().clear();

        String input = "todo walk dog";
        try {
            Action action = Action.userAction(input, false, false, ui);
            action.runTask(taskManager);
        } catch (TabbyException e) {
            System.err.println(e.getMessage());
        }
        ArrayList<Task> taskList = taskManager.getTasks();
        String output = "";
        for (Task task : taskList) {
            output = task.toString();
        }

        String expected = "[T][ ] walk dog";
        assertEquals(expected, output);
    }

    @Test
    public void addDeadlineTest() {
        Ui ui = new Ui();

        Storage storage = new Storage(DIRECTORY, FILENAME, ui);
        TaskManager taskManager = new TaskManager(storage, ui);
        taskManager.getTasks().clear();

        String input = "deadline return book /by 2/12/2019 1800";
        try {
            Action action = Action.userAction(input, false, true, ui);
            action.runTask(taskManager);
        } catch (TabbyException e) {
            System.err.println(e.getMessage());
        }
        ArrayList<Task> taskList = taskManager.getTasks();
        String output = "";
        for (Task task : taskList) {
            output = task.toString();
        }

        String expected = "[D][ ] return book (by: Dec 02 2019, 6:00 pm)";
        assertEquals(expected, output);
    }


    @Test
    public void addEventTest() {
        Ui ui = new Ui();

        Storage storage = new Storage(DIRECTORY, FILENAME, ui);
        TaskManager taskManager = new TaskManager(storage, ui);
        taskManager.getTasks().clear();

        String input = "event project meeting /from 2/12/2019 1800 /to 2/12/2019 2000";
        try {
            Action action = Action.userAction(input, false, true, ui);
            action.runTask(taskManager);
        } catch (TabbyException e) {
            System.err.println(e.getMessage());
        }
        ArrayList<Task> taskList = taskManager.getTasks();
        String output = "";
        for (Task task : taskList) {
            output = task.toString();
        }

        String expected = "[E][ ] project meeting (from: Dec 02 2019, 6:00 pm to: Dec 02 2019, 8:00 pm)";
        assertEquals(expected, output);
    }
}
