import java.util.Scanner;

public class IDKName {
    private String name;
    private Scanner scanner;
    private final String line = "_".repeat(60);

    public IDKName() {
        this.name = "IDKName";
        this.scanner = new Scanner(System.in);
    }

    private String greetings() {
        return String.format("%s" +
                "%nHello! I'm %s" +
                "%nWhat can I do for you?" +
                "%n%s", this.line, this.name, this.line);
    }

    private String goodbye() {
        return String.format("Bye. Hope to see you again!" +
                "%n%s", this.line);
    }

    private void echo() {
        String userInput;
        while (true) {
            System.out.print("Message Prompt (type bye to exit): ");
            userInput = scanner.nextLine();
            System.out.println(this.line);
            if (userInput.toLowerCase().equals("bye")) {
                break;
            }
            String message = String.format("%s%n%s", userInput, this.line);
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        IDKName chatbot = new IDKName();
        System.out.println(chatbot.greetings());
        chatbot.echo();
        System.out.println(chatbot.goodbye());
    }
}
