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
        return input.split(" ", 2);
    }

    /**
     * Converts a user-specified task number into a zero-based index.
     *
     * @param taskNumber the task number as a string (1-based index)
     * @return the zero-based task index
     * @throws NumberFormatException if the task number is not a valid integer
     */
    public static int getTaskId(String taskNumber) {
        return  Integer.parseInt(taskNumber) - 1;
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
        String[] subParts = description.split("/");
        if (subParts.length < 2) {
            return null;
        }
        String deadlineDescription = subParts[0].trim();
        String by = subParts[1].trim().replace("by ", "");
        return new String[] {deadlineDescription, by};
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
        String[] subParts = description.split("/");
        if (subParts.length < 3) {
            return null;
        }
        String eventDescription = subParts[0].trim();
        String from = subParts[1].trim();
        String to = subParts[2].trim();
        return new String[] {eventDescription, from, to};
    }

    /**
     * Parses a date string in ISO-8601 format (yyyy-MM-dd) into a {@link LocalDate}.
     *
     * @param date the date string to parse
     * @return the corresponding LocalDate
     * @throws java.time.format.DateTimeParseException if the string is not in the correct format
     */
    public static LocalDate localDateParse(String date) {
        return LocalDate.parse(date);
    }

    /**
     * Parses a datetime string in ISO-8601 format (yyyy-MM-ddTHH:mm[:ss]) into a {@link LocalDateTime}.
     *
     * @param date the datetime string to parse
     * @return the corresponding LocalDateTime
     * @throws java.time.format.DateTimeParseException if the string is not in the correct format
     */
    public static LocalDateTime localDateTimeParse(String date) {
        return LocalDateTime.parse(date);
    }
}
