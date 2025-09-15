package idkname.main;

import java.io.IOException;
import java.util.Objects;

import idkname.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
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
            Font.loadFont(getClass().getResourceAsStream("/fonts/Pokemon_FireRedLeafGreen.ttf"), 14);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("IdKName");
            MainWindow controller = loader.getController();
            controller.setDuke(chatbot);

            stage.setOnShown(e -> controller.appendBot(Objects
                    .requireNonNullElse(chatbot.getGreeting(), "Hello! I'm IdKName\nWhat can I do for you!")
            ));

            stage.setOnCloseRequest(e -> {
                try {
                    controller.appendBot(Objects.requireNonNullElse(chatbot.getGoodbye(),
                            "Bye. Hope to see you again soon!"));
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
