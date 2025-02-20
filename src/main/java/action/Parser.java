package action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.TabbyExceptionIncompleteCommand;
import exceptions.TabbyExceptionInvalidCommand;
import exceptions.TabbyExceptionInvalidDeadlineInput;
import exceptions.TabbyExceptionInvalidEventInput;

/**
 * The Parser class is responsible for handling user and file input parsing.
 * It provides methods to validate, parse tasks, deadlines, and events.
 */
public class Parser {

    // User input patterns
    private static final Pattern DEADLINE_USER_INPUT_PATTERN = Pattern.compile("(.+?)\\s*/by\\s*(.+)");
    private static final Pattern EVENT_USER_INPUT_PATTERN =
            Pattern.compile("(.+?)\\s*/from\\s*(.+?)\\s*/to\\s*(.+)");

    // File input patterns
    private static final Pattern DEADLINE_FILE_INPUT_PATTERN = Pattern.compile("(.+?)\\s*\\(by:\\s*(.+?)\\)");
    private static final Pattern EVENT_FILE_INPUT_PATTERN =
            Pattern.compile("(.+?)\\s*\\(from:\\s*(.+?)\\s*to:\\s*(.+?)\\)");
    private static final Pattern FILE_INPUT_TASK_PATTERN = Pattern.compile("^\\[(T|E|D)\\]\\[(X| )\\]\\s*(.*)$");

    // Date formatters
    private static final DateTimeFormatter USER_INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Validates if the input is null or empty.
     *
     * @param input The user input string.
     * @return true if input is null or empty, otherwise false.
     */
    public static boolean validateInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Parses the task details from the file input.
     *
     * @param input The input string from the file.
     * @return A HashMap containing task details.
     */
    public static HashMap<String, String> parseFileRead(String input) {
        Matcher matcher = FILE_INPUT_TASK_PATTERN.matcher(input);
        HashMap<String, String> taskDetails = new HashMap<>();

        if (matcher.matches()) {
            switch (matcher.group(1)) {
            case "T":
                taskDetails.put("task", "todo");
                break;
            case "D":
                taskDetails.put("task", "deadline");
                break;
            case "E":
                taskDetails.put("task", "event");
                break;
            default:
                break;
            }
            taskDetails.put("status", matcher.group(2).equals("X") ? "true" : "false");
            taskDetails.put("description", matcher.group(3));
        }

        return taskDetails;
    }

    /**
     * Parses the user input and returns the parsed task components.
     *
     * @param input The user input string.
     * @return An array containing the parsed command and arguments.
     * @throws TabbyExceptionInvalidCommand If the command is invalid.
     * @throws TabbyExceptionIncompleteCommand If the command is incomplete.
     */
    public static String[] parseTask(String input) throws TabbyExceptionInvalidCommand,
            TabbyExceptionIncompleteCommand {

        String trimmedInput = input.trim();
        if (validateInput(trimmedInput)) {
            throw new TabbyExceptionInvalidCommand();
        }

        if (trimmedInput.equalsIgnoreCase("list") || trimmedInput.equalsIgnoreCase("reminder")) {
            return new String[]{trimmedInput};
        }

        String[] tokens = trimmedInput.split("\\s+", 2);

        if (tokens.length < 2) {
            throw new TabbyExceptionIncompleteCommand();
        }
        return new String[]{tokens[0].trim(), tokens[1].trim()};
    }

    /**
     * Parses a deadline task input.
     *
     * @param input The input string.
     * @param isUserInput Whether the input is from a user or a file.
     * @return A HashMap containing deadline details.
     * @throws TabbyExceptionInvalidDeadlineInput If the input format is incorrect.
     */
    public static HashMap<String, String> parseDeadline(String input, boolean isUserInput)
            throws TabbyExceptionInvalidDeadlineInput {
        Pattern pattern = isUserInput ? DEADLINE_USER_INPUT_PATTERN : DEADLINE_FILE_INPUT_PATTERN;
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new TabbyExceptionInvalidDeadlineInput();
        }

        HashMap<String, String> deadlineDetails = new HashMap<>();
        String description = matcher.group(1).trim();
        String dateTimeStr = matcher.group(2).trim();

        try {
            deadlineDetails.put("description", description);

            if (isUserInput) {
                LocalDateTime deadline = LocalDateTime.parse(dateTimeStr, USER_INPUT_FORMATTER);
                deadlineDetails.put("by", deadline.format(OUTPUT_FORMATTER));
            } else {
                deadlineDetails.put("by", dateTimeStr);
            }

            return deadlineDetails;
        } catch (DateTimeParseException e) {
            throw new TabbyExceptionInvalidDeadlineInput();
        }
    }

    /**
     * Parses an event task input.
     *
     * @param input The input string.
     * @param isUserInput Whether the input is from a user or a file.
     * @return A HashMap containing event details.
     * @throws TabbyExceptionInvalidEventInput If the input format is incorrect.
     */
    public static HashMap<String, String> parseEvent(String input, boolean isUserInput)
            throws TabbyExceptionInvalidEventInput {
        Pattern pattern = isUserInput ? EVENT_USER_INPUT_PATTERN : EVENT_FILE_INPUT_PATTERN;
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new TabbyExceptionInvalidEventInput();
        }

        HashMap<String, String> eventDetails = new HashMap<>();
        String description = matcher.group(1).trim();
        String fromTimeStr = matcher.group(2).trim();
        String toTimeStr = matcher.group(3).trim();

        try {
            eventDetails.put("description", description);

            if (isUserInput) {
                LocalDateTime fromTime = LocalDateTime.parse(fromTimeStr, USER_INPUT_FORMATTER);
                LocalDateTime toTime = LocalDateTime.parse(toTimeStr, USER_INPUT_FORMATTER);

                eventDetails.put("from", fromTime.format(OUTPUT_FORMATTER));
                eventDetails.put("to", toTime.format(OUTPUT_FORMATTER));
            } else {
                eventDetails.put("from", fromTimeStr);
                eventDetails.put("to", toTimeStr);
            }

            return eventDetails;
        } catch (DateTimeParseException e) {
            throw new TabbyExceptionInvalidEventInput();
        }
    }
}
