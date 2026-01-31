# IDKName

---
## 🤖 Chatbot Overview

- IDKName Chatbot is a JavaFX desktop app for managing tasks with simple commands.

- It features a retro game-inspired interface and a task manager backend.

- Users type commands in the input box, and the bot replies in the main chat area with feedback, errors, or the task list.

---

## 🚀 Features
- Java 17 support
- Pre-configured project structure (`src/main/java`, `src/test/java`)
- Sample tests included
- Checkstyle for code quality
- Gradle build system
- Simple UI implemented with JavaFX

---

## 🛠️ Setting Up in IntelliJ

### Prerequisites
- [JDK 17](https://adoptium.net/) installed
- IntelliJ IDEA (latest version recommended)

### Steps
1. Open IntelliJ (if you have a project open, click **File > Close Project** first).
2. Open this project:
   - Click **Open**.
   - Select the project directory.
   - Click **OK** and accept defaults for any further prompts.
3. Configure the project to use **JDK 17**:
   - Go to **File > Project Structure > Project**.
   - Set **Project SDK** to JDK 17.
   - Set **Project language level** to **SDK default**.
4. Run the main class:
   - Navigate to `src/main/java/idkname/IDKName.java`.
   - Right-click it and choose **Run 'IDKName.main()'**.

If setup is correct, you can move on to the chatbot!


## 📖 Command Descriptions
```
1. list
Shows all tasks currently in your task list, along with their type and completion status.

2. bye
Exits the chatbot gracefully and saves all tasks to file.

3. mark <task-id>
Marks the task with the given ID as completed.

4. unmark <task-id>
Unmarks the task with the given ID (sets it back to not done).

5. find <keyword>
Searches the task list for tasks containing the keyword in their description.

6. todo <description>
Creates a simple todo task with the given description.

7. deadline <description> / yyyy-MM-dd
Creates a deadline task with the given description and a due date.

8. event <description> / yyyy-MM-dd'T'HH:mm:ss / yyyy-MM-dd'T'HH:mm:ss
Creates an event task scheduled between a start and end date/time

9. sort
Sorts all tasks in the list by type (Todos, Deadlines, Events).

10. sort <event, deadline, todo>
Sorts and displays tasks filtered by the given type.

11. help
If you need any help for the instructions!
```
- Eg. Usage
    ```bash
    todo Read book
    deadline Submit report / 2025-09-30
    event Team meeting / 2025-09-15T14:00:00 / 2025-09-15T15:00:00
---

## 📦 Building

This project uses **Gradle** as its build tool.

- To build:
    ```bash
    ./gradlew build

- To run tests:
    ```bash
    ./gradlew test
