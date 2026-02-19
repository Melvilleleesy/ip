package idkname.utility;

import java.time.DateTimeException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles all user interactions with the application, including
 * greetings, goodbye messages, error messages, and command input.
 */
public class Command {
    private final Scanner scanner;
    private final String name;
    private final TaskList tasks;

    /**
     * Creates a new {@code Command} facade bound to a bot name and task list.
     *
     * @param name  bot/display name used in greetings
     * @param tasks the task list this command handler operates on
     */
    public Command(String name, TaskList tasks) {
        this.scanner = new Scanner(System.in);
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Returns the message shown when no save file is found during load.
     *
     * @return user-facing error message for missing save file
     */
    public String showFileLoadingError() {
        return "No saved file.";
    }

    /**
     * Returns the message shown when a supplied date/time cannot be parsed.
     * <p>Expected formats (examples):</p>
     * <ul>
     *   <li>{@code deadline description/yyyy-MM-dd}</li>
     *   <li>{@code event description/yyyy-MM-dd'T'HH:mm:ss/yyyy-MM-dd'T'HH:mm:ss}</li>
     * </ul>
     *
     * @return user-facing error message describing valid date/time formats
     */
    public String showDateTimeError() {
        return String.format("Invalid date format.Please enter as:"
                + "%n-deadline description/%nyyyy-MM-dd"
                + "%n-event description/%nyyyy-MM-ddTHH:mm:ss/%nyyyy-MM-ddTHH:mm:ss");
    }

    /**
     * Returns the message shown when a task index is outside the valid range.
     *
     * @return user-facing error message for an invalid task number
     */
    public String showIndexOutOfBoundsError() {
        return "Task not found. Please enter a valid task number.";
    }

    /**
     * Returns the message shown when a numeric argument is malformed
     * (e.g., a non-integer where a task index is expected).
     *
     * @return user-facing hint on the expected command + task number format
     */
    public String showNumberFormatError() {
        return String.format("Please enter a valid command:"
                + "%n[command] [task number]");
    }

    /**
     * Builds a numbered, line-separated view of the provided tasks.
     *
     * @param taskList the tasks to format
     * @return formatted list, one task per line with 1-based indexing
     */
    public String printTaskList(TaskList taskList) {
        var tasks = taskList.getTasks(); // ArrayList<Task>
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> String.format("%d. %s%n", i + 1, tasks.get(i)))
                .collect(Collectors.joining());
    }

    /**
     * Returns the greeting shown at application start.
     *
     * @return greeting message including the bot name
     */
    public String greetings() {
        return String.format("Hello! I'm %s"
                + "%nWhat can I do for you?", this.name);
    }

    /**
     * Returns the farewell shown when the program exits.
     *
     * @return goodbye message
     */
    public String goodbye() {
        return "Bye. Hope to see you again!";
    }

    /**
     * Returns the message shown when the command word is not recognized.
     *
     * @return user-facing error message for unknown commands and a brief hint
     */
    private String showUnknownCommandError() {
        return String.format("OOPS!!! I'm sorry, but I don't know what that means :-("
                + "%nPlease enter with a valid command"
                + "%n(Eg. todo, deadline, event, find, mark, unmark, list, bye)%n");
    }

    /**
     * Returns the message shown when a command is missing required arguments.
     * Provides usage examples for valid commands.
     *
     * @return user-facing error message plus example usages
     */
    private String showMissingArgumentError() {
        return String.format("OOPS!!! I'm sorry, but I don't know what that means :-("
                + "%nPlease enter a valid command and a valid instruction"
                + "%nTry typing \"help\" to find out what are valid instructions!");
    }

    /**
     * Provides a help message listing all supported user commands and their usage.
     * <p>
     * The help message includes examples of how to use each command:
     * <ul>
     *   <li><code>list</code> — display all tasks</li>
     *   <li><code>bye</code> — exit the program</li>
     *   <li><code>sort</code> — sort all tasks by type</li>
     *   <li><code>mark &lt;id&gt;</code>/<code>unmark &lt;id&gt;</code> — mark or unmark a task by ID</li>
     *   <li><code>find &lt;keyword&gt;</code> — search for tasks containing a keyword</li>
     *   <li><code>todo &lt;description&gt;</code> — add a todo task</li>
     *   <li><code>deadline &lt;description&gt; / yyyy-MM-dd</code> — add a deadline task with a due date</li>
     *   <li><code>event &lt;description&gt; / yyyy-MM-ddTHH:mm:ss / yyyy-MM-ddTHH:mm:ss</code>
     *       — add an event task with start and end times</li>
     *   <li><code>sort &lt;taskType&gt;</code> — sort and display tasks filtered by type
     *       (e.g., <code>sort deadline</code>)</li>
     *   <li><code>help</code> — display this help message</li>
     * </ul>
     *
     * @return a formatted string containing the list of valid instructions and usage examples
     */
    private String showHelp() {
        return String.format("Try any of these valid instructions!"
                + "%nEg. usage: "
                + "%n1) list"
                + "%n2) bye"
                + "%n3) sort"
                + "%n4) mark/unmark task id"
                + "%n5) find description"
                + "%n6) todo description"
                + "%n7) deadline description/%nyyyy-mm-dd"
                + "%n8) event description/%nyyyy-MM-ddTHH:mm:ss/%nyyyy-MM-ddTHH:mm:ss"
                + "%n9) sort tasktype"
                + "%n10) help");
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
     *       — add an event task</li>
     *   <li><code>find &lt;keyword&gt;</code> — search for tasks matching a keyword</li>
     *   <li><code>sort</code> — sort all tasks by type (todo, deadline, event)</li>
     *   <li><code>sort &lt;todo|deadline|event&gt;</code> — sort and display tasks filtered by type</li>
     * </ul>
     * Handles invalid commands and errors gracefully by showing appropriate error messages.
     */
    public String getResponse(String userInput) {
        String[] parts = Parser.ordinaryParse(userInput);
        String command = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : null;

        try {
            return switch (command) {
                case "bye" -> "Bye. Hope to see you again soon!\n";
                case "list" -> printTaskList(tasks) + "\n";
                case "help" -> showHelp();
                case "mark" -> tasks.markDoneOrUndone(true, arg);
                case "unmark" -> tasks.markDoneOrUndone(false, arg);
                case "delete" -> tasks.delete(arg);
                case "todo" -> tasks.add("todo", arg);
                case "deadline" -> tasks.add("deadline", arg);
                case "event" -> tasks.add("event", arg);
                case "find" -> printTaskList(tasks.find(arg));
                case "sort" -> printTaskList(arg == null
                        ? tasks.sortTasks()
                        : tasks.sortTasks(arg));
                default -> (arg == null
                        ? showMissingArgumentError()
                        : showUnknownCommandError());
            };
        } catch (NumberFormatException e) {
            return showNumberFormatError();
        } catch (IndexOutOfBoundsException e) {
            return showIndexOutOfBoundsError();
        } catch (DateTimeException e) {
            return showDateTimeError();
        }
    }
}
