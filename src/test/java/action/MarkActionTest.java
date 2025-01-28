package action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import exceptions.TabbyException;
import org.junit.jupiter.api.Test;
import tabby.Ui;
import task.Storage;
import task.TaskManager;
import task.Task;

import java.util.ArrayList;

public class MarkActionTest {
    private static final String DIRECTORY = "./data";
    private static final String FILENAME = "tabby_data.txt";

    @Test
    public void MarkTest() {
        Ui ui = new Ui();

        Storage storage = new Storage(DIRECTORY, FILENAME, ui);
        TaskManager taskManager = new TaskManager(storage, ui);
        taskManager.getTasks().clear();

        String add_input = "todo walk dog";
        String mark_input = "mark 1";
        try {
            Action add_action = Action.userAction(add_input, false, false, ui);
            add_action.runTask(taskManager);
            Action mark_action = Action.userAction(mark_input, false, false, ui);
            mark_action.runTask(taskManager);
        } catch (TabbyException e) {
            System.err.println(e.getMessage());
        }
        ArrayList<Task> taskList = taskManager.getTasks();
        String output = "";
        for (Task task : taskList) {
            output = task.toString();
        }

        String expected = "[T][X] walk dog";
        assertEquals(expected, output);
    }

    @Test
    public void MarkInvalidTest() {
        Ui ui = new Ui();

        Storage storage = new Storage(DIRECTORY, FILENAME, ui);
        TaskManager taskManager = new TaskManager(storage, ui);
        taskManager.getTasks().clear();

        String mark_input = "mark 2";
        try {
            Action mark_action = Action.userAction(mark_input, false, false, ui);
            mark_action.runTask(taskManager);
        } catch (TabbyException e) {
            System.err.println(e.getMessage());
        }
        ArrayList<Task> taskList = taskManager.getTasks();
        String output = "";
        for (Task task : taskList) {
            output = task.toString();
        }

        String expected = "";
        assertEquals(expected, output);
    }
}