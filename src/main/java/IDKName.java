import java.util.Scanner;

public class IDKName {
    private String name;
    private Scanner scanner;
    private final String line = "_".repeat(60);
    private Task[] list;
    private int currentListPos;

    public IDKName() {
        this.name = "IDKName";
        this.scanner = new Scanner(System.in);
        this.list = new Task[100];
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
            System.out.print("Message Prompt: ");
            userInput = scanner.nextLine();
            String[] parts = userInput.split(" ");
            System.out.println(this.line);

            try {
                String firstPart = parts[0];
                String adjustedFirstPart = firstPart.toLowerCase();
                if (adjustedFirstPart.equals("bye")) {
                    break;
                } else if (adjustedFirstPart.equals("list")) {
                    for (int i = 0; i < this.currentListPos; i++) {
                        System.out.println(String.format("%d. %s", i + 1, this.list[i].toString()));
                    }
                    System.out.println(this.line);
                } else if (adjustedFirstPart.equals("mark") || adjustedFirstPart.equals("unmark")) {
                    if (parts.length < 2) {
                        System.out.println("Please specify task number");
                    } else {
                        int taskNumber = Integer.parseInt(parts[1]) - 1;
                        Task t = this.list[taskNumber];
                        if (adjustedFirstPart.equals("mark")) {
                            t.markDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(t);
                        } else {
                            t.markUndone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(t);
                        }
                    }
                }
                else {
                    String message = String.format("added: %s%n%s", userInput, this.line);
                    this.addList(userInput);
                    System.out.println(message);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid task number");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid command");
            }
        }
    }

    private void addList(String item) {
        this.list[this.currentListPos] = new Task(item);
        this.currentListPos++;
    }

    public static void main(String[] args) {
        IDKName chatbot = new IDKName();
        System.out.println(chatbot.greetings());
        chatbot.echo();
        System.out.println(chatbot.goodbye());
    }
}
