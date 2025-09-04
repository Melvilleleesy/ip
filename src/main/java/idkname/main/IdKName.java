package idkname.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import idkname.utility.Storage;
import idkname.utility.TaskList;
import idkname.utility.Command;

/**
 * Main entry point for the IDKName task management application.
 * <p>
 * This class coordinates between the user interface ({@link Command}),
 * the task list ({@link TaskList}), and the storage system ({@link Storage}).
 * It handles initialization, running the user interaction loop,
 * and saving/loading tasks from persistent storage.
 */
public class IdKName {
    private final TaskList list;
    private final Storage storage;
    private final Command command;

    /**
     * Constructs a new instance of the IDKName application.
     * Initializes the task list, UI, and storage, and attempts
     * to load tasks from the given file path.
     *
     * @param filePath the path to the storage file for saving and loading tasks
     */
    public IdKName(String filePath) {
        assert filePath != null : "filePath must not be null";

        this.list = new TaskList();
        this.command = new Command("IDKName", this.list);
        this.storage = new Storage(this.list, filePath);

        try {
            this.storage.load();
        } catch (FileNotFoundException e) {
            command.showFileLoadingError();
        } catch (DateTimeParseException e) {
            command.showDateTimeError();
        }
    }

    /**
     * Returns the initial greeting message shown to the user when the application starts.
     *
     * @return the greeting message
     */
    public String getGreeting() {
        System.out.println("greeting works");
        return command.greetings();
    }

    /**
     * Returns the farewell message shown to the user when the application exits.
     *
     * @return the goodbye message
     */
    public String getGoodbye() {
        System.out.println("goodbye works");
        return command.goodbye();
    }

    /**
     * Persists the current state of the application before exit.
     *
     * @throws IOException if an error occurs while saving data to storage
     */
    public void persistOnExit() throws IOException {
        System.out.println("save works");
        storage.save();
    }

    /**
     * Generates a response for the given user input.
     *
     * @param input the raw user input string
     * @return the application's response to the input
     */
    public String getResponse(String input) {
        return command.getResponse(input);
    }
}
