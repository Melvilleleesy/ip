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
    public String showFileLoadingError() {
        return "No saved file.";
    }

    /**
     * Displays an error message if the user provides an invalid date or time format.
     * The expected formats are:
     * <ul>
     *   <li>deadline description/yyyy-MM-dd</li>
     *   <li>event description/yyyy-MM-ddTHH:mm:ss/yyyy-MM-ddTHH:mm:ss</li>
     * </ul>
     */
    public String showDateTimeError() {
        return String.format("Invalid date format.Please enter as:"
                + "%n-deadline description/yyyy-MM-dd"
                + "%n-event description/yyyy-MM-ddTHH:mm:ss/yyyy-MM-ddTHH:mm:ss");
    }

    /**
     * Displays an error message if task data cannot be saved to disk.
     */
    public String showIoError() {
        return "Unable to save data.";
    }

    /**
     * Displays an error message if the user specifies an invalid task index.
     */
    public String showIndexOutOfBoundsError() {
        return "Task not found. Please enter a valid task number.";
    }

    /**
     * Displays an error message if the user provides an invalid number format
     * (e.g. a non-integer where a task index is expected).
     */
    public String showNumberFormatError() {
        return String.format("Please enter a valid command:"
                + "%n[command] [task number]");
    }

    /**
     * Iterates and prints the list of tasks
     *
     * @param taskList list of tasks to be printed
     */
    public String printTaskList(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.getTasks().size(); i++) {
            sb.append(String.format("%d. %s%n", i + 1, taskList.getTasks().get(i).toString()));
        }
        return sb.toString();
    }

    /**
     * Greets the user at the start of the program.
     */
    public String greetings() {
        return String.format("Hello! I'm %s"
                + "%nWhat can I do for you?", this.name);
    }

    /**
     * Displays a goodbye message when the program exits.
     */
    public String goodbye() {
        return "Bye. Hope to see you again!";
    }

    /**
     * Returns the error message shown when the user enters
     * an unknown command that the application cannot recognize.
     *
     * @return a formatted error message indicating an unrecognized command
     */
    public String showUnknownCommandError() {
        return String.format("OOPS!!! I'm sorry, but I don't know what that means :-("
                + "%nPlease enter with a valid command"
                + "%n(Eg. todo, deadline, event, find, mark, unmark, list, bye)%n");
    }

    /**
     * Returns the error message shown when the user enters
     * a command without the required arguments.
     *
     * @return a formatted error message indicating that arguments are missing,
     *         along with usage examples for valid commands
     */
    public String showMissingArgumentError() {
        return String.format("OOPS!!! I'm sorry, but I don't know what that means :-("
                + "%nPlease enter a valid command and a valid instruction"
                + "%nEg. usage: "
                + "%n-list"
                + "%n-bye"
                + "%n-mark/unmark task id"
                + "%n-find description"
                + "%n-todo description"
                + "%n-deadline description / yyyy-mm-dd"
                + "%n-event description / yyyy-MM-dd'T'HH:mm:ss / yyyy-MM-dd'T'HH:mm:ss");
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
     *   <li><code>event &lt;description&gt; /from yyyy-MM-ddTHH:mm:ss /to yyyy-MM-ddTHH:mm:ss</code>
     *   <li><code>find &lt;description&gt;</code></li>
     *   — add an event task</li>
     * </ul>
     * Handles invalid commands and errors gracefully by showing appropriate error messages.
     */
    public String getResponse(String userInput) {
        String[] parts = Parser.ordinaryParse(userInput);
        StringBuilder out = new StringBuilder();

        try {
            String command = parts[0].toLowerCase();
            if (command.equals("bye")) {
                out.append("Bye. Hope to see you again soon!\n");
            } else if (command.equals("list")) {
                out.append(printTaskList(tasks)).append('\n');
            } else if (parts.length > 1) {
                switch (command) {
                case "mark" -> out.append(this.tasks.markDoneOrUndone(true, parts[1]));
                case "unmark" -> out.append(this.tasks.markDoneOrUndone(false, parts[1]));
                case "delete" -> out.append(this.tasks.delete(parts[1]));
                case "todo" -> out.append(this.tasks.add("todo", parts[1]));
                case "deadline" -> out.append(this.tasks.add("deadline", parts[1]));
                case "event" -> out.append(this.tasks.add("event", parts[1]));
                case "find" -> out.append(printTaskList(this.tasks.find(parts[1])));
                default -> out.append(showUnknownCommandError());
                }
            } else {
                out.append(showMissingArgumentError());
            }
        } catch (NumberFormatException e) {
            out.append(showNumberFormatError());
        } catch (IndexOutOfBoundsException e) {
            out.append(showIndexOutOfBoundsError());
        } catch (DateTimeException e) {
            out.append(showDateTimeError());
        }

        return out.toString();
    }

}
