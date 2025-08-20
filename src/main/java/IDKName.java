public class IDKName {
    private String name;

    public IDKName() {
        this.name = "IDKName";
    }

    private String greetings() {
        String line = "_".repeat(60);
        return String.format("%s" +
                "%nHello! I'm %s" +
                "%nWhat can I do for you?" +
                "%n%s" +
                "%nBye. Hope to see you soon!" +
                "%n%s", line, this.name, line, line);
    }

    public static void main(String[] args) {
        IDKName chatbot = new IDKName();
        System.out.println(chatbot.greetings());
    }
}
