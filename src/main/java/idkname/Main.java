package idkname;

import idkname.task.Event;
import idkname.task.Deadline;
import idkname.task.Todo;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        IDKName chatbot = new IDKName("./data/IDKName.txt");
        chatbot.run();
    }
}
