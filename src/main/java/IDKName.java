import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class IDKName {
    private final String line = "_".repeat(75);
    private final TaskList list;
    private final Storage storage;
    private final Ui ui;

    public IDKName(String filePath) {
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

    public void run() {
        ui.greetings();
        ui.echo();
        try {
            this.storage.save();
        } catch (IOException e) {
            ui.showIOError();
        }
        ui.goodbye();
    }
}
