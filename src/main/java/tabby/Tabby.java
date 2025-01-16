package tabby;

import java.util.ArrayList;
import java.util.Scanner;
import task.Task;

/**
 * A class representing a chatbot
 */
public class Tabby {
    private static final String CHATBOT = "Tabby";
    private ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Generates a greeting message for the chatbot.
     *
     * @return A greeting message string.
     */
    private String greeting() {
        return String.format("Hello! I'm %s.\nWhat can I do for you?", CHATBOT);
    }

    /**
     * Reads user input from the console, allowing the user to interact with
     * the chatbot by adding tasks or viewing the task list. Exits when the
     * user types "bye".
     *
     * @return A goodbye message string when the program ends.
     */
    public String readUserInput() {
        Scanner sc = new Scanner(System.in); // Create Scanner
        while (true) {
            String userInput = sc.nextLine().trim();
            if (userInput.equals("bye")) {
                break; // Exit loop when user types "bye"
            }
            if (userInput.equals("list")) {
                displayTaskList(); // Displays task list
            } else {
                addTask(userInput);
            }
        }
        sc.close(); // Close Scanner after loop
        return goodbye(); // Return goodbye message
    }

    /**
     * Adds a new task to the task list and displays a confirmation message.
     *
     * @param description The description of the task to be added.
     */
    public void addTask(String description) {
        Task task = new Task(description);
        taskList.add(task);
        System.out.println(String.format("Added: %s\n", task));
    }

    /**
     * Prints the user's input with a prefix.
     *
     * @param userInput The user's input string to be echoed back.
     */
    private void echo(String userInput) {
        System.out.printf("%s\n", userInput); // Display user input
    }

    /**
     * Displays all tasks currently stored in the task list
     */
    private void displayTaskList() {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf(String.format("%d. %s\n", i + 1, taskList.get(i)));
        }
    }

    /**
     * Generates a goodbye message for the chatbot.
     *
     * @return A goodbye message string.
     */
    private String goodbye() {
        return "Bye. Hope to see you again soon!";
    }

    public static void main(String[] args) {
        Tabby tabby = new Tabby();

        // Print the greeting message
        System.out.printf(String.format("%s\n", tabby.greeting()));

        // Read and respond to user input
        System.out.printf(String.format("%s\n", tabby.readUserInput()));
    }
}
