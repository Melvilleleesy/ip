package idkname.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class that handles parsing of user input strings into
 * structured values such as task descriptions, IDs, and dates/times.
 */
public class Parser {
    /**
     * Splits a line of user input into at most two parts:
     * the first word (usually the command) and the rest of the line.
     *
     * @param input the raw user input
     * @return a string array of length 1 (if only command given)
     *         or 2 (command and remaining content)
     */
    public static String[] ordinaryParse(String input) {
        assert input != null : "input must not be null";
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return new String[] { "" };
        }
        return trimmed.split("\\s+", 2);
    }

    /**
     * Converts a user-specified task number into a zero-based index.
     *
     * @param taskNumber the task number as a string (1-based index)
     * @return the zero-based task index
     * @throws NumberFormatException if the task number is not a valid integer
     */
    public static int getTaskId(String taskNumber) {
        assert taskNumber != null : "taskNumber must not be null";
        String s = taskNumber.trim();
        int n = Integer.parseInt(s);
        if (n <= 0) {
            throw new NumberFormatException("Task id must be >= 1");
        }
        return n - 1;
    }

    /**
     * Parses a deadline description of the form
     * "description /by yyyy-mm-dd".
     *
     * @param description the user input string
     * @return an array of length 2: [description, dueDateString],
     *         or null if the input is invalid
     */
    public static String[] deadlineParse(String description) {
        assert description != null : "description must not be null";
        String s = description.trim();
        if (s.isEmpty()) {
            return null;
        }

        String[] parts = s.split("\\s*/\\s*", 2);
        if (parts.length != 2) {
            return null;
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) {
            return null;
        }

        return new String[] { desc, by };
    }


    /**
     * Parses an event description of the form
     * "description /from yyyy-mm-ddTHH:mm /to yyyy-mm-ddTHH:mm".
     *
     * @param description the user input string
     * @return an array of length 3: [description, startString, endString],
     *         or null if the input is invalid
     */
    public static String[] eventParse(String description) {
        assert description != null : "description must not be null";
        String s = description.trim();
        if (s.isEmpty()) {
            return null;
        }

        String[] left = s.split("\\s*/\\s*", 2);
        if (left.length != 2) {
            return null;
        }

        String desc = left[0].trim();
        String[] right = left[1].split("\\s*/\\s*", 2);
        if (right.length != 2) {
            return null;
        }

        String start = right[0].trim();
        String end = right[1].trim();
        if (desc.isEmpty() || start.isEmpty() || end.isEmpty()) {
            return null;
        }

        try {
            LocalDateTime.parse(start);
            LocalDateTime.parse(end);
        } catch (Exception e) {
            return null;
        }

        return new String[] { desc, start, end };
    }

    /**
     * Parses a date string in ISO-8601 format (yyyy-MM-dd) into a {@link LocalDate}.
     *
     * @param date the date string to parse
     * @return the corresponding LocalDate
     * @throws java.time.format.DateTimeParseException if the string is not in the correct format
     */
    public static LocalDate localDateParse(String date) {
        assert date != null : "date string must not be null";
        String s = date.trim();
        return LocalDate.parse(s);
    }

    /**
     * Parses a datetime string in ISO-8601 format (yyyy-MM-ddTHH:mm[:ss]) into a {@link LocalDateTime}.
     *
     * @param date the datetime string to parse
     * @return the corresponding LocalDateTime
     * @throws java.time.format.DateTimeParseException if the string is not in the correct format
     */
    public static LocalDateTime localDateTimeParse(String date) {
        assert date != null : "date string must not be null";
        String s = date.trim();
        return LocalDateTime.parse(s);
    }
}
