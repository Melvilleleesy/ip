package idkname;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import idkname.utility.Storage;
import idkname.utility.TaskList;
import idkname.utility.Ui;

/**
 * Main entry point for the IDKName task management application.
 * <p>
 * This class coordinates between the user interface ({@link Ui}),
 * the task list ({@link TaskList}), and the storage system ({@link Storage}).
 * It handles initialization, running the user interaction loop,
 * and saving/loading tasks from persistent storage.
 */
public class IdKName {
    private final TaskList list;
    private final Storage storage;
    private final Ui ui;

    /**
     * Constructs a new instance of the IDKName application.
     * Initializes the task list, UI, and storage, and attempts
     * to load tasks from the given file path.
     *
     * @param filePath the path to the storage file for saving and loading tasks
     */
    public IdKName(String filePath) {
        this.list = new TaskList();
        this.ui = new Ui("IDKName", this.list);
        this.storage = new Storage(this.list, filePath);
        try {
            this.storage.load();
        } catch (FileNotFoundException e) {
            ui.showFileLoadingError();
        } catch (DateTimeParseException e) {
            ui.showDateTimeError();
        }
    }

    /**
     * Runs the main application loop.
     * <ul>
     *     <li>Greets the user</li>
     *     <li>Handles user commands interactively until "bye" is entered</li>
     *     <li>Saves the task list to persistent storage</li>
     *     <li>Displays a goodbye message</li>
     * </ul>
     */

    public String getGreeting() { return ui.greetings(); }

    public String getGoodbye() { return ui.goodbye(); }

    public void persistOnExit() throws IOException { storage.save(); }

    public String getResponse(String input) {
        return ui.getResponse(input);
    }
}
