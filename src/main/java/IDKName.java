import java.util.Scanner;

public class IDKName {
    private String name;
    private Scanner scanner;
    private final String line = "_".repeat(60);
    private String[] list;
    private int currentListPos;

    public IDKName() {
        this.name = "IDKName";
        this.scanner = new Scanner(System.in);
        this.list = new String[100];
        this.currentListPos = 0;
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
            System.out.print("Message Prompt ('bye': exit, 'list': list ");
            userInput = scanner.nextLine();
            System.out.println(this.line);
            if (userInput.toLowerCase().equals("bye")) {
                break;
            } else if (userInput.toLowerCase().equals("list")) {
                for (int i = 0; i < this.currentListPos; i++) {
                    System.out.println(String.format("%d. %s", i + 1, this.list[i]));
                }
                System.out.println(this.line);
            } else {
                String message = String.format("added: %s%n%s", userInput, this.line);
                this.addList(userInput);
                System.out.println(message);
            }
        }
    }

    private void addList(String item) {
        this.list[this.currentListPos] = item;
        this.currentListPos++;
    }

    public static void main(String[] args) {
        IDKName chatbot = new IDKName();
        System.out.println(chatbot.greetings());
        chatbot.echo();
        System.out.println(chatbot.goodbye());
    }
}
