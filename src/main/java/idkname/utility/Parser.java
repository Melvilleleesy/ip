package idkname.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Parser {
    public static String[] ordinaryParse(String input) {
        return input.split(" ", 2);
    }

    public static int getTaskId(String taskNumber) {
        return Integer.parseInt(taskNumber) - 1;
    }

    public static String[] deadlineParse(String description) {
        String[] subParts = description.split("/");
        if (subParts.length < 2) {
            return null;
        }
        String deadlineDescription = subParts[0].trim();
        String by = subParts[1].trim().replace("by ", "");
        return new String[] {deadlineDescription, by};
    }

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

    public static LocalDate localDateParse(String date) {
        return LocalDate.parse(date);
    }

    public static LocalDateTime localDateTimeParse(String date) {
        return LocalDateTime.parse(date);
    }
}
