# Tabby Chatbot User Guide

Tabby is a chatbot that helps users manage their tasks efficiently. It supports **To-Dos, Deadlines, and Events**, making task tracking simple and organized.

![Tabby Chatbot UI](Ui.png)

## Quick Start

1. Ensure you have **Java 11 or later** installed on your computer.
2. Download the latest `tabby.jar` file from [Releases](https://github.com/varsha13152/ip/releases).
3. Open a command terminal and navigate to the folder containing `duke.jar`.
4. Run the following command to start Duke:
   
   ```sh
   java -jar tabby.jar

## Commands and Formats

| **Command**  | **Format** |
|-------------|-----------------------------------------|
| **List** | `list` |
| **Reminder** | `reminder` |
| **Mark as Done** | `mark <task number>` |
| **Unmark as Not Done** | `unmark <task number>` |
| **Delete Task** | `delete <task number>` |
| **Add Deadline** | `deadline <description> /by <date time>` |
| **Add Event** | `event <description> /from <start> /to <end>` |
| **Add To-Do** | `todo <description>` |
| **Exit** | `bye` |

## Saving and Editing Data
- Tabby **automatically saves your tasks** after every command execution.
- The data is stored in a file called `tabby.txt` inside the `data` folder.
- If the `tabby.txt` file is missing, Tabby will create a new one with an empty task list.
- **Manually editing `tabby.txt`**:
  - You can modify `tabby.txt` using a text editor.
  - Ensure that the format remains unchanged to avoid data corruption.

