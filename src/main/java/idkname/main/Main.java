package idkname.main;

import java.io.IOException;

import idkname.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import idkname.ui.MainWindow;


/**
 * Entry point for the IDKName chatbot application.
 * <p>
 * This class initializes the chatbot with a storage file path
 * and starts the application loop.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "./data/IDKName.txt";
    private final IdKName chatbot = new IdKName(DEFAULT_FILE_PATH);
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
        assert stage != null : "Stage must not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "Root AnchorPane should be loaded";

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.<MainWindow>getController();
            assert controller != null : "MainWindow controller not injected by FXML";
            controller.setDuke(chatbot);

            stage.setOnShown(e -> {
                controller.appendBot((chatbot.getGreeting() != null)
                        ? chatbot.getGreeting()
                        : "Hello! I'm IdKName\nWhat can I do for you?"
                );
            });

            // add: on close, append goodbye and persist safely
            stage.setOnCloseRequest(e -> {
                try {
                    controller.appendBot((chatbot.getGoodbye() != null)
                            ? chatbot.getGoodbye()
                            : "Bye. Hope to see you again soon!"
                    );
                    chatbot.persistOnExit();
                } catch (Exception ex) {
                    controller.appendBot("Error saving: " + ex.getMessage());
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
