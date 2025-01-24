package action;
import exceptions.*;
import java.util.regex.*;
import java.util.HashMap;

public class Parser {
    private static final Pattern DEADLINE_USER_INPUT_PATTERN = Pattern.compile("(.+?)\\s*/by\\s*(.+)");
    private static final Pattern DEADLINE_FILE_INPUT_PATTERN = Pattern.compile("(.+?)\\s*\\(by:\\s*(.+?)\\)");
    private static final Pattern FILE_INPUT_PATTERN_REGEX = Pattern.compile("^\\[(T|E|D)\\]\\[(X| )\\]\\s*(.*)$");
    private static final Pattern EVENT_USER_INPUT_PATTERN = Pattern.compile("(.+?)\\s*/from\\s*(.+?)\\s*/to\\s*(.+)");
    private static final Pattern EVENT_FILE_INPUT_PATTERN = Pattern.compile("(.+?)\\s*\\(from:\\s*(.+?)\\s*to:\\s*(.+?)\\)");

    public static boolean validateInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static HashMap<String, String> parseFileRead(String input) {
        Matcher matcher = FILE_INPUT_PATTERN_REGEX.matcher(input);
        HashMap<String, String> taskDetails = new HashMap<>();

        if (matcher.matches()) {
            switch (matcher.group(1)) {
                case "T":
                    taskDetails.put("task", "todo");
                    break;
                case "D":
                    taskDetails.put("task", "deadline");
                    break;
                default:
                    taskDetails.put("task", "event");
            }
            taskDetails.put("status", matcher.group(2).equals("X") ? "true" : "false");
            taskDetails.put("description", matcher.group(3));
        }

        return taskDetails;
    }

    public static String[] parseTask(String input) throws TabbyExceptionIncompleteCommand, TabbyExceptionInvalidCommand {
        if (validateInput(input)) {
            throw new TabbyExceptionIncompleteCommand();
        }

        if (input.equals("list")) {
            return new String[] {input.trim()};
        }

        String[] tokens = input.trim().split("\\s+", 2);

        if(tokens.length < 2) {
            throw new TabbyExceptionInvalidCommand();
        }
        return new String[]{tokens[0].trim(), tokens[1].trim()};
    }

    public static HashMap<String, String> parseDeadline(String input, boolean isUserInput) throws TabbyExceptionInvalidDeadlineInput {
        Pattern pattern = isUserInput ? DEADLINE_USER_INPUT_PATTERN : DEADLINE_FILE_INPUT_PATTERN;
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new TabbyExceptionInvalidDeadlineInput();
        }

        HashMap<String, String> deadlineDetails = new HashMap<>();
        deadlineDetails.put("description", matcher.group(1).trim());
        deadlineDetails.put("by", matcher.group(2).trim());

        return deadlineDetails;
    }

    public static HashMap<String, String> parseEvent(String input, boolean isUserInput) throws TabbyExceptionInvalidEventInput {
        Pattern pattern = isUserInput ? EVENT_USER_INPUT_PATTERN : EVENT_FILE_INPUT_PATTERN;
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new TabbyExceptionInvalidEventInput();
        }

        HashMap<String, String> eventDetails = new HashMap<>();
        eventDetails.put("description", matcher.group(1).trim());
        eventDetails.put("from", matcher.group(2).trim());
        eventDetails.put("to", matcher.group(3).trim());

        return eventDetails;
    }
}