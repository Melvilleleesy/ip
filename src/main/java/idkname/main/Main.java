package idkname;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Entry point for the IDKName chatbot application.
 * <p>
 * This class initializes the chatbot with a storage file path
 * and starts the application loop.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "./data/IDKName.txt";
    private IdKName chatbot = new IdKName(DEFAULT_FILE_PATH);
    /**
     * Starts the IDKName chatbot application.
     * <p>
     * The chatbot loads tasks from <code>./data/IDKName.txt</code>
     * if available, and begins interacting with the user.
     *
     * @param stage stage to build gui
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setDuke(chatbot);  // inject the Duke instance

            stage.setOnShown(e -> {
                // prefer calling a bot/UI method that returns a String greeting
                // e.g. chatbot.getGreeting() or chatbot.ui.greetings()
                // If you don't have one yet, you can inline a string temporarily.
                controller.appendBot(
                        (chatbot.getGreeting() != null) ? chatbot.getGreeting() : "Hello! I'm IdKName\nWhat can I do for you?"
                );
            });

            // add: on close, append goodbye and persist safely
            stage.setOnCloseRequest(e -> {
                // show goodbye in the chat UI
                try {
                    // same note: prefer a method that returns the goodbye text
                    controller.appendBot(
                            (chatbot.getGoodbye() != null) ? chatbot.getGoodbye() : "Bye. Hope to see you again soon!"
                    );
                    // save if your IdKName exposes a persist method
                    chatbot.persistOnExit();
                } catch (Exception ex) {
                    controller.appendBot("Error saving: " + ex.getMessage());
                }
                // allow close to proceed
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
