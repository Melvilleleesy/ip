import java.util.Scanner;

public class    IDKName {
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
            String[] parts = userInput.split(" ", 2);
            System.out.println(this.line);

            try {
                String command = parts[0];
                String commandLowerCase = command.toLowerCase();
                if (commandLowerCase.equals("bye")) {
                    break;
                } else if (commandLowerCase.equals("list")) {
                    for (int i = 0; i < this.currentListPos; i++) {
                        System.out.println(String.format("%d. %s", i + 1, this.list[i].toString()));
                    }
                    System.out.println(this.line);
                } else if (commandLowerCase.equals("mark") || commandLowerCase.equals("unmark")) {
                    if (parts.length < 2) {
                        System.out.println("Please specify task number");
                    } else {
                        int taskNumber = Integer.parseInt(parts[1]) - 1;
                        Task t = this.list[taskNumber];
                        if (commandLowerCase.equals("mark")) {
                            t.markDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(t);
                        } else {
                            t.markUndone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(t);
                        }
                    }
                } else {
                    if (commandLowerCase.equals("todo")) {
                        System.out.println("Got it. I've added this task:");
                        this.addList(0, parts[1]);
                        System.out.println(this.list[this.currentListPos - 1].toString());
                        System.out.println(String.format("Now you have %d tasks in the list.", this.currentListPos));
                    } else if (commandLowerCase.equals("deadline")) {
                        System.out.println("Got it. I've added this task:");
                        this.addList(1, parts[1]);
                        System.out.println(this.list[this.currentListPos - 1].toString());
                        System.out.println(String.format("Now you have %d tasks in the list.", this.currentListPos));
                    } else if (commandLowerCase.equals("event")){
                        System.out.println("Got it. I've added this task:");
                        this.addList(2, parts[1]);
                        System.out.println(this.list[this.currentListPos - 1].toString());
                        System.out.println(String.format("Now you have %d tasks in the list.", this.currentListPos));
                    } else {
                        System.out.println("Please enter with a command (Eg. todo, deadline, event, list, bye)");
                    }
                    System.out.println(this.line);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid task number");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid command");
            }
        }
    }

    private void addList(int i, String item) {
        if (i == 0) {
            this.list[this.currentListPos] = new Todo(item);
        } else if (i == 1) {
            String[] parts = item.split("/");
            String description = parts[0].trim();
            String by = parts[1].trim();
            String when = by.replace("by ", "");
            this.list[this.currentListPos] = new Deadline(description, when);
        } else {
            String[] parts = item.split("/");
            String description = parts[0].trim();
            String from = parts[1].trim();
            String to = parts[2];
            this.list[this.currentListPos] = new Event(description, from, to);
        }
        this.currentListPos++;
    }

    public static void main(String[] args) {
        IDKName chatbot = new IDKName();
        System.out.println(chatbot.greetings());
        chatbot.echo();
        System.out.println(chatbot.goodbye());
    }
}
