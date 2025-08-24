package idkname.utility;

import java.time.DateTimeException;
import java.util.Scanner;

/**
 * Handles all user interactions with the application, including
 * greetings, goodbye messages, error messages, and command input.
 */
public class Ui {
    private final Scanner scanner;
    private final String name;
    private final String line = "_".repeat(75);
    private final TaskList tasks;

    /**
     * Constructs a Ui instance with a bot name and a reference to the task list.
     *
     * @param name  the name of the bot
     * @param tasks the task list to operate on
     */
    public Ui(String name, TaskList tasks) {
        this.scanner = new Scanner(System.in);
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Displays an error message if no save file is found when loading tasks.
     */
    public void showFileLoadingError() {
        System.out.printf("No saved file.%n%s%n", this.line);
    }

    /**
     * Displays an error message if the user provides an invalid date or time format.
     * The expected formats are:
     * <ul>
     *   <li>deadline description/yyyy-MM-dd</li>
     *   <li>event description/yyyy-MM-ddTHH:mm:ss/yyyy-MM-ddTHH:mm:ss</li>
     * </ul>
     */
    public void showDateTimeError() {
        System.out.printf("Invalid date format.Please enter as:" +
                "%n-deadline description/yyyy-MM-dd" +
                "%n-event description/yyyy-MM-ddTHH:mm:ss/yyyy-MM-ddTHH:mm:ss%n%s%n", this.line);
    }

    /**
     * Displays an error message if task data cannot be saved to disk.
     */
    public void showIOError() {
        System.out.printf("Unable to save data.%n%s%n", this.line);
    }

    /**
     * Displays an error message if the user specifies an invalid task index.
     */
    public void showIndexOutOfBoundsError() {
        System.out.printf("Task not found. Please enter a valid task number.%n%s%n",
                this.line);
    }

    /**
     * Displays an error message if the user provides an invalid number format
     * (e.g. a non-integer where a task index is expected).
     */
    public void showNumberFormatError() {
        System.out.printf("Please enter a valid command:" +
                "%n[command] [task number]%n%s%n", this.line);
    }

    /**
     * Greets the user at the start of the program.
     */
    public void greetings() {
        System.out.printf("%s" +
                "%nHello! I'm %s" +
                "%nWhat can I do for you?" +
                "%n%s%n", this.line, this.name, this.line);
    }

    /**
     * Displays a goodbye message when the program exits.
     */
    public void goodbye() {
        System.out.printf("Bye. Hope to see you again!" +
                "%n%s", this.line);
    }

    /**
     * Main loop for handling user input.
     * Reads commands from the user, parses them, and executes the corresponding actions.
     * Supports commands such as:
     * <ul>
     *   <li><code>bye</code> — exit the program</li>
     *   <li><code>list</code> — display all tasks</li>
     *   <li><code>mark &lt;id&gt;</code> — mark a task as done</li>
     *   <li><code>unmark &lt;id&gt;</code> — unmark a task</li>
     *   <li><code>delete &lt;id&gt;</code> — delete a task</li>
     *   <li><code>todo &lt;description&gt;</code> — add a todo task</li>
     *   <li><code>deadline &lt;description&gt; /by yyyy-MM-dd</code> — add a deadline task</li>
     *   <li><code>event &lt;description&gt; /from yyyy-MM-ddTHH:mm:ss /to yyyy-MM-ddTHH:mm:ss</code> — add an event task</li>
     * </ul>
     * Handles invalid commands and errors gracefully by showing appropriate error messages.
     */
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
                this.showNumberFormatError();
            } catch (IndexOutOfBoundsException e) {
                this.showIndexOutOfBoundsError();
            } catch (DateTimeException e) {
                this.showDateTimeError();
            }
        }
    }
}
