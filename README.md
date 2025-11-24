# CSE2006 Project: GUI Calculator

## Introduction
This project implements a simple Graphical User Interface (GUI) calculator using Java Swing. It allows users to perform basic arithmetic operations (addition, subtraction, multiplication, division) and maintains a history of calculations, demonstrating core Java concepts, object-oriented programming principles, exception handling, and file I/O.

## Setup Instructions
1.  **Ensure Java Development Kit (JDK) is installed**: This project requires Java 8 or newer.
2.  **Save the file**: Save the provided `Main.java` source code into a directory of your choice.

## How to Run
1.  **Compile the Java code**: Open a terminal or command prompt, navigate to the directory where you saved `Main.java`, and compile it using the Java compiler:
    ```bash
    javac Main.java
    ```
2.  **Run the application**: After successful compilation, run the application:
    ```bash
    java Main
    ```
    A calculator window will appear, ready for use.

## Course Objectives Met
This project was designed to address the following objectives from the CSE2006 course syllabus:

1.  **Java Introduction & Flow Control (Variables, Operators, Loops, if/else)**:
    *   **Demonstrated by**: Use of variables (`result`, `currentInput`, `lastOperator`), arithmetic operators (`+`, `-`, `*`, `/`), `if/else` and `switch` statements for managing calculator logic (e.g., determining operation, handling clear/delete, preventing multiple decimal points, division by zero check).

2.  **OOP (Classes, Objects, Inheritance, Polymorphism, Encapsulation)**:
    *   **Demonstrated by**: The `Main` class extends `JFrame`, showcasing **inheritance**. `displayField` (JTextField), `JButton` instances, and `ActionEvent` are all **objects**. The `Main` class itself **encapsulates** all UI components and the calculator's operational logic. The `actionPerformed` method handles events polymorphically via the `ActionListener` interface.

3.  **Exception Handling (try-catch) & Multithreading (Thread lifecycle or GUI Event Dispatching)**:
    *   **Demonstrated by**: `try-catch` blocks are used to handle potential `NumberFormatException` when parsing user input from the `displayField` and `IOException` during file operations (reading/writing `history.txt`). **GUI Event Dispatching** is intrinsically demonstrated by the use of `SwingUtilities.invokeLater()` to ensure UI updates are handled safely on the Event Dispatch Thread (EDT), crucial for responsive GUI applications.

4.  **I/O Streams (Reading/Writing data to files)**:
    *   **Demonstrated by**: The `appendToHistory` method uses `FileWriter` and `PrintWriter` to write calculation results to `history.txt`, ensuring persistent storage. The "History" button reads from `history.txt` using `java.nio.file.Files.readAllLines` to display past calculations to the user, demonstrating file reading capabilities.