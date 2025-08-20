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
            String[] parts = userInput.split(" ", 2);
            System.out.println(this.line);

            try {
                String command = parts[0];
                String commandLowerCase = command.toLowerCase();
                if (commandLowerCase.equals("bye")) {
                    break;
                } else if (commandLowerCase.equals("list")) {
                    for (int i = 0; i < this.currentListPos; i++) {
                        System.out.printf("%d. %s%n", i + 1, this.list[i].toString());
                    }
                    System.out.println(this.line);
                } else if (commandLowerCase.equals("mark") || commandLowerCase.equals("unmark")) {
                    if (parts.length < 2) {
                        System.out.println("Please specify task number");
                    } else {
                        int taskNumber = Integer.parseInt(parts[1]) - 1;
                        Task t = this.list[taskNumber];
                        if (t == null) {
                            System.out.println("Please enter a valid task number.");
                        }
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
                        if (this.addList(0, parts[1])) {
                            System.out.println("Got it. I've added this task:");
                            System.out.println(this.list[this.currentListPos - 1].toString());
                            System.out.printf("Now you have %d tasks in the list.%n", this.currentListPos);
                        }
                    } else if (commandLowerCase.equals("deadline")) {
                        if (this.addList(1, parts[1])) {
                            System.out.println("Got it. I've added this task:");
                            System.out.println(this.list[this.currentListPos - 1].toString());
                            System.out.printf("Now you have %d tasks in the list.%n", this.currentListPos);
                        }
                    } else if (commandLowerCase.equals("event")){
                        if (this.addList(2, parts[1])) {
                            System.out.println("Got it. I've added this task:");
                            System.out.println(this.list[this.currentListPos - 1].toString());
                            System.out.printf("Now you have %d tasks in the list.%n", this.currentListPos);
                        }
                    } else {
                        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                        System.out.println("Please enter with a valid command");
                        System.out.println("(Eg. todo, deadline, event, list, bye)");
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

    private boolean addList(int i, String item) {
        if (item.isEmpty()) {
            return false;
        }
            if (i == 0) {
                this.list[this.currentListPos] = new Todo(item);
            } else if (i == 1) {
                String[] parts = item.split("/");
                if (parts.length < 2) {
                    return false;
                }
                String description = parts[0].trim();
                String by = parts[1].trim().replace("by ", "");
                this.list[this.currentListPos] = new Deadline(description, by);
            } else {
                String[] parts = item.split("/");
                if (parts.length < 3) {
                    return false;
                }
                String description = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                this.list[this.currentListPos] = new Event(description, from, to);
            }
            this.currentListPos++;
            return true;
    }

    public void run() {
        System.out.println(greetings());
        echo();
        System.out.println(goodbye());
    }
}
