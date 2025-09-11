package idkname.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Task;
import idkname.task.Todo;

/**
 * Handles reading from and writing to a persistent storage file
 * that contains the user's task list.
 * <p>
 * Tasks are stored in plain text format, with fields separated by "|".
 * Supported task types: Todo (T), Deadline (D), Event (E).
 */
public class Storage {
    private final TaskList tasks;
    private final String filePath;

    /**
     * Constructs a Storage object responsible for persisting a given task list.
     *
     * @param tasks    the task list to read from or write to
     * @param filePath the path to the storage file
     */
    public Storage(TaskList tasks, String filePath) {
        this.tasks = tasks;
        this.filePath = filePath;
    }

    /**
     * Saves the current tasks to the storage file.
     * Creates parent directories if they do not exist.
     * <p>
     * Tasks are serialized in the following formats:
     * <ul>
     *   <li>Todo: {@code T | doneFlag | description}</li>
     *   <li>Deadline: {@code D | doneFlag | description | yyyy-MM-dd}</li>
     *   <li>Event: {@code E | doneFlag | description | yyyy-MM-ddTHH:mm | yyyy-MM-ddTHH:mm}</li>
     * </ul>
     *
     * @throws IOException if an error occurs during writing
     */
    public void save() throws IOException {
        File f = new File(this.filePath);
        System.out.println("save at: " + this.filePath);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (FileWriter fw = new FileWriter(f)) {
            for (Task t : this.tasks.getTasks()) {
                String taskType = t.getTaskType();
                int taskDone = t.isDone() ? 1 : 0;
                String taskDescription = t.getDescription();
                String textToAdd = String.format("%s | %d | %s",
                        taskType, taskDone, taskDescription);

                switch (taskType) {
                case "D":
                    LocalDate dueDate = t.getDueDate();
                    textToAdd = textToAdd + " | " + dueDate;
                    break;
                case "E":
                    LocalDateTime[] timePeriod = t.getTimePeriod();
                    textToAdd = textToAdd + " | " + timePeriod[0] + " | " + timePeriod[1];
                    break;
                default:
                    break;
                }
                fw.write(textToAdd);
                fw.write(System.lineSeparator());
            }
        }
        System.out.println("Saving to: " + new File(this.filePath).getAbsolutePath());
    }

    /**
     * Loads tasks from the storage file into the associated task list.
     * Ignores invalid or malformed lines.
     * <p>
     * Expected line formats are the same as in {@link #save()}.
     *
     * @throws FileNotFoundException if the storage file does not exist
     */
    public void load() throws FileNotFoundException {
        File f = new File(this.filePath);
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s*\\|\\s*", 5);
                if (parts.length < 3) continue;

                String taskType = parts[0].trim(); // "T","D","E"
                String taskDone = parts[1].trim(); // "0" or "1"
                String desc = parts[2].trim();

                Task t;
                switch (taskType) {
                case "T" -> t = new Todo(desc);
                case "D" -> {
                    if (parts.length < 4) continue;
                    try {
                        LocalDate due = Parser.localDateParse(parts[3].trim());
                        t = new Deadline(desc, due);
                    } catch (Exception ex) {
                        continue;
                    }
                }
                case "E" -> {
                    if (parts.length < 5) continue;
                    try {
                        LocalDateTime start = Parser.localDateTimeParse(parts[3].trim());
                        LocalDateTime end   = Parser.localDateTimeParse(parts[4].trim());
                        t = new Event(desc, start, end);
                    } catch (Exception ex) {
                        continue;
                    }
                }
                default -> { continue; }
                }

                if ("1".equals(taskDone)) {
                    t.markDone(true); // true implies that this comes from loading so no printed message
                }
                this.tasks.add(t);
            }
        }
    }
}
