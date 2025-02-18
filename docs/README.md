# Tabby Chatbot User Guide

Tabby is a chatbot that helps users manage their tasks efficiently. It supports **To-Dos, Deadlines, and Events**, making task tracking simple and organized.

![Tabby Chatbot UI](Ui.png)

## Installation

1. Ensure you have **Java 11 or later** installed on your computer
2. Download the latest `tabby.jar` file from [Releases](https://github.com/varsha13152/ip/releases)
3. Open a command terminal and navigate to the folder containing `tabby.jar`
4. Run the following command to start Tabby:

```sh
java -jar tabby.jar
```

## Available Commands

### Basic Commands

* **List Tasks**: View all your current tasks
  ```sh
  list
  ```

* **View Reminders**: Check upcoming deadlines and events
  ```sh
  reminder
  ```

* **Exit Application**: Close Tabby
  ```sh
  bye
  ```

### Task Management

* **Add To-Do**: Create a simple task
  ```sh
  todo <description>
  ```

* **Add Deadline**: Create a task with a due date
  ```sh
  deadline <description> /by <date time>
  ```

* **Add Event**: Schedule an event with start and end times
  ```sh
  event <description> /from <start> /to <end>
  ```

* **Mark Task as Done**: Complete a task
  ```sh
  mark <task number>
  ```

* **Unmark Task**: Revert a task to incomplete status
  ```sh
  unmark <task number>
  ```

* **Delete Task**: Remove a task from your list
  ```sh
  delete <task number>
  ```

## Data Storage and Management

Tabby manages your data with the following features:

* Automatic saving after every command execution
* Data storage in `tabby.txt` within the `data` folder
* Automatic file creation if `tabby.txt` is missing
* Manual editing support through any text editor (maintain file format to prevent corruption)
```