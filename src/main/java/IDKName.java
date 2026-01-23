import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IDKName {
    private final String name;
    private final Scanner scanner;
    private final String line = "_".repeat(60);
    private final String filePath;
    private final ArrayList<Task> list;

    public IDKName() {
        this.name = "IDKName";
        this.scanner = new Scanner(System.in);
        this.list = new ArrayList<>();
        this.filePath = "./data/IDKName.txt";
    }

    private String greetings() {
        try {
            this.load();
        } catch (FileNotFoundException e) {
            System.out.print("No saved file.");
        }
        return String.format("%s" +
                "%nHello! I'm %s" +
                "%nWhat can I do for you?" +
                "%n%s", this.line, this.name, this.line);
    }

    private String goodbye() {
        try {
            this.save();
        } catch (IOException e) {
            System.out.println("Unable to save data.");
        }
        return String.format("Bye. Hope to see you again!" +
                "%n%s", this.line);
    }

    private void markDoneOrUndone(boolean b, String taskNumber) {
        int task_Id = Integer.parseInt(taskNumber) - 1; // may throw NumberFormatException
        Task t = this.list.get(task_Id);
        if (b) {
            t.markDone();
        } else {
            t.markUndone();
        }
    }

    private void delete(String taskNumber) {
        int task_Id = Integer.parseInt(taskNumber) - 1; // may throw NumberFormatException
        Task t = this.list.get(task_Id);
        System.out.print("Noted. I've removed this task:");
        System.out.println(t.toString());
        this.list.remove(task_Id);
        System.out.printf("Now you have %d tasks in the list.%n",
                this.list.size());
    }

    private void save() throws IOException {
        File file = new File(this.filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (FileWriter fw = new FileWriter(file)) {
            for (Task t : this.list) {
                String taskType = t.getTaskType();
                int taskDone = t.getMark() ? 1 : 0;
                String taskDescription = t.getDescription();
                String textToAdd = String.format("%s | %d | %s",
                        taskType, taskDone, taskDescription);

                switch (taskType) {
                    case "D":
                        String dueDate = t.getDueDate();
                        textToAdd = textToAdd + " | " + dueDate;
                        break;
                    case "E":
                        String[] timePeriod = t.getTimePeriod();
                        textToAdd = textToAdd + " | " + timePeriod[0] + "-" + timePeriod[1];
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

    private void load() throws FileNotFoundException {
        File f = new File(this.filePath);
        if (!f.exists()) {
            return;
        }
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s*\\|\\s*", 4);
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
                        String dueDate = parts[3].trim();
                        t = new Deadline(taskDescription, dueDate);
                        break;
                    case "E":
                        if (parts.length < 4) continue;
                        String timePeriod = parts[3].trim();
                        String[] subParts = timePeriod.split("\\s*-\\s*", 2);
                        String start = subParts[0];
                        String end = subParts[1];
                        t = new Event(taskDescription, start, end);
                        break;
                    default:
                        continue;
                }
                if ("1".equals(taskDone)) {
                    t.markDone();
                }
                this.list.add(t);
            }
        }
    }

    private void echo() {
        String userInput;
        while (true) {
            System.out.print("Message Prompt: ");
            userInput = scanner.nextLine();
            String[] parts = userInput.split(" ", 2); // split (command, total description)
            System.out.println(this.line);

            try {
                String command = parts[0];
                String commandLowerCase = command.toLowerCase();
                if (commandLowerCase.equals("bye")) {
                    break;
                } else if (commandLowerCase.equals("list")) {
                    for (int i = 0; i < this.list.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, this.list.get(i).toString());
                    }
                    System.out.println(this.line);
                } else if (parts.length > 1) {
                    switch (commandLowerCase) {
                        case "mark" -> this.markDoneOrUndone(true, parts[1]);
                        case "unmark" -> this.markDoneOrUndone(false, parts[1]);
                        case "delete" -> this.delete(parts[1]);
                        case "todo" -> this.addList("todo", parts[1]);
                        case "deadline" -> this.addList("deadline", parts[1]);
                        case "event" -> this.addList("event", parts[1]);
                        default -> System.out.printf("OOPS!!! I'm sorry, but I don't know what that means :-(" +
                                "%nPlease enter with a valid command" +
                                "%n(Eg. todo, deadline, event, list, bye)%n");}
                    System.out.println(this.line);
                } else {
                    System.out.printf("OOPS!!! I'm sorry, but I don't know what that means :-(" +
                            "%nPlease enter a valid command and a valid instruction" +
                            "%n(Eg. todo [instruction], deadline [instruction])%n%s%n", this.line);
                }
            } catch (NumberFormatException e) {
                System.out.printf("Please enter a valid command:" +
                        "%n[command] [task number]%n%s%n", this.line);
            } catch (IndexOutOfBoundsException e) {
                System.out.printf("Task not found. Please enter a valid task number.%n%s",
                        this.line);
            }
        }

    }

    private void addList(String type, String item) {
        Task t = null;
        switch (type) {
        case "todo":
            t = new Todo(item); // add description normally
            this.list.add(t);
            break;
        case "deadline":
            String[] deadlineParts = item.split("/"); // split item into description and time
            if (deadlineParts.length < 2) {
                return;
            }
            String deadlineDescript = deadlineParts[0].trim();
            String by = deadlineParts[1].trim().replace("by ", "");
            t = new Deadline(deadlineDescript, by);
            this.list.add(t);
            break;
        case "event":
            String[] eventParts = item.split("/"); // split item into description, time period
            if (eventParts.length < 3) {
                return;
            }
            String eventDescript = eventParts[0].trim();
            String from = eventParts[1].trim();
            String to = eventParts[2].trim();
            t = new Event(eventDescript, from, to);
            this.list.add(t);
            break;
        default:
            break;
        }
        System.out.printf("Got it. I've added this task:" +
                        "%n %s" +
                        "%nNow you have %d tasks in the list.%n",
                t, this.list.size()); // t will never be null as we check if type and item exist

    }

    public void run() {
        System.out.println(greetings());
        echo();
        System.out.println(goodbye());
    }
}
