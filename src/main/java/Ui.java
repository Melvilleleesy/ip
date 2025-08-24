import java.io.IOException;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    private final String name;
    private final String line = "_".repeat(75);
    private final TaskList tasks;

    public Ui(String name, TaskList tasks) {
        this.scanner = new Scanner(System.in);
        this.name = name;
        this.tasks = tasks;
    }

    public void showFileLoadingError() {
        System.out.printf("No saved file.%n%s%n", this.line);
    }

    public void showDateTimeError() {
        System.out.printf("Invalid date format.Please enter as:" +
                "%n-deadline description/yyyy-MM-dd" +
                "%n-event description/yyyy-MM-ddTHH:mm:ss%n%s%n", this.line);
    }

    public void showIOError() {
        System.out.printf("Unable to save data.%n%s%n", this.line);
    }

    public void greetings() {
        System.out.printf("%s" +
                "%nHello! I'm %s" +
                "%nWhat can I do for you?" +
                "%n%s%n", this.line, this.name, this.line);
    }

    public void goodbye() {
        System.out.printf("Bye. Hope to see you again!" +
                "%n%s", this.line);
    }

    public void echo() {
        String userInput;
        while (true) {
            System.out.print("Message Prompt: ");
            userInput = scanner.nextLine();
            String[] parts = Parser.ordinaryParse(userInput); // split (command, total description)
            System.out.println(this.line);

            try {
                String command = parts[0];
                String commandLowerCase = command.toLowerCase();
                if (commandLowerCase.equals("bye")) {
                    break;
                } else if (commandLowerCase.equals("list")) {
                    for (int i = 0; i < this.tasks.getTasks().size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, this.tasks.get(i).toString());
                    }
                    System.out.println(this.line);
                } else if (parts.length > 1) {
                    switch (commandLowerCase) {
                        case "mark" -> this.tasks.markDoneOrUndone(true, parts[1]);
                        case "unmark" -> this.tasks.markDoneOrUndone(false, parts[1]);
                        case "delete" -> this.tasks.delete(parts[1]);
                        case "todo" -> this.tasks.add("todo", parts[1]);
                        case "deadline" -> this.tasks.add("deadline", parts[1]);
                        case "event" -> this.tasks.add("event", parts[1]);
                        default -> System.out.printf("OOPS!!! I'm sorry, but I don't know what that means :-(" +
                                "%nPlease enter with a valid command" +
                                "%n(Eg. todo, deadline, event, list, bye)%n");
                    }
                    System.out.println(this.line);
                } else {
                    System.out.printf("OOPS!!! I'm sorry, but I don't know what that means :-(" +
                            "%nPlease enter a valid command and a valid instruction" +
                            "%nEg. usage: " +
                            "%n-list" +
                            "%n-bye" +
                            "%n-mark/unmark task id" +
                            "%n-todo description" +
                            "%n-deadline description / yyyy-mm-dd" +
                            "%n-event description / yyyy-MM-dd'T'HH:mm:ss / yyyy-MM-dd'T'HH:mm:ss%n%s%n", this.line);
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
}
