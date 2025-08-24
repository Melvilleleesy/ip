package idkname;

/**
 * Entry point for the IDKName chatbot application.
 * <p>
 * This class initializes the chatbot with a storage file path
 * and starts the application loop.
 */
public class Main {
    /**
     * Starts the IDKName chatbot application.
     * <p>
     * The chatbot loads tasks from <code>./data/IDKName.txt</code>
     * if available, and begins interacting with the user.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        IdKName chatbot = new IdKName("./data/IDKName.txt");
        chatbot.run();
    }
}
