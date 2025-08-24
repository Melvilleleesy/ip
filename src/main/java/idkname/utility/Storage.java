package idkname.utility;

import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Task;
import idkname.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Storage {
    private final TaskList tasks;
    private final String filePath;

    public Storage(TaskList tasks, String filePath) {
        this.tasks = tasks;
        this.filePath = filePath;
    }

    public void save() throws IOException {
        File file = new File(this.filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (FileWriter fw = new FileWriter(file)) {
            for (Task t : this.tasks.getTasks()) {
                String taskType = t.getTaskType();
                int taskDone = t.getMark() ? 1 : 0;
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
            fw.close();
        }
        System.out.println("Saving to: " + new File(this.filePath).getAbsolutePath());
    }

    public void load() throws FileNotFoundException {
        File f = new File(this.filePath);
        if (!f.exists()) {
            return;
        }
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s*\\|\\s*", 5);
                if (parts.length < 3) continue;

                // will always have 3 parts as that's the basic necessity to create a task
                String taskType = parts[0].trim(); // "T", "D", "E"
                String taskDone = parts[1].trim(); // "0" or "1"
                String taskDescription = parts[2].trim();
                Task t;
                switch (taskType) {
                    case "T":
                        t = new Todo(taskDescription);
                        break;
                    case "D":
                        if (parts.length < 4) continue;
                        String date = parts[3].trim();
                        LocalDate dueDate = Parser.localDateParse(date);
                        t = new Deadline(taskDescription, dueDate);
                        break;
                    case "E":
                        if (parts.length < 4) continue;
                        String start = parts[3].trim();
                        String end =  parts[4].trim();
                        LocalDateTime startDate = Parser.localDateTimeParse(start);
                        LocalDateTime endDate = Parser.localDateTimeParse(end);
                        t = new Event(taskDescription, startDate, endDate);
                        break;
                    default:
                        continue;
                }
                if ("1".equals(taskDone)) {
                    t.markDone(false);
                }
                this.tasks.add(t);
            }
        }
    }
}
