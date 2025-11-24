# Project Report: GUI Calculator (CSE2006)

## 1. Introduction
This report details the design and implementation of a Graphical User Interface (GUI) Calculator developed as a project for the CSE2006 course. The primary objective of this project was to construct a functional calculator application that leverages core Java programming concepts, object-oriented programming (OOP) principles, robust exception handling, and efficient file I/O operations. The application provides a user-friendly interface for performing basic arithmetic calculations and maintains a persistent history of all operations, showcasing practical application of theoretical knowledge acquired during the course.

## 2. Objective
The main objectives for this project were multifaceted:
*   To design and implement a fully functional GUI-based calculator using Java Swing/AWT.
*   To demonstrate proficiency in fundamental Java syntax, flow control mechanisms (conditionals, loops), and data types.
*   To apply Object-Oriented Programming (OOP) principles, including inheritance, encapsulation, and object instantiation.
*   To integrate comprehensive exception handling strategies to gracefully manage runtime errors such as invalid input or file access issues.
*   To implement file I/O operations for reading and writing data, specifically by maintaining a log of all calculations in a text file (`history.txt`).
*   To ensure the application is robust, user-friendly, and adheres to good programming practices.

## 3. System Analysis: Object-Oriented Approach
The GUI Calculator project is structured with a strong emphasis on Object-Oriented Programming (OOP) principles. The `Main` class serves as the central component, embodying the calculator application itself. This class extends `JFrame`, which is a fundamental component of Java Swing for creating top-level windows. This extension demonstrates **inheritance**, a cornerstone of OOP, allowing `Main` to inherit all the functionalities and characteristics of a `JFrame` and then specialize them for a calculator application.

**Encapsulation** is evident throughout the design. The `Main` class encapsulates all the graphical user interface elements such as `JTextField` (for display) and `JButton` objects (for numbers and operators), as well as the internal logic for performing calculations (e.g., `result`, `currentInput`, `lastOperator` variables and the `calculate` method). These internal states and behaviors are managed within the class, exposing only necessary functionalities to the outside through methods like `actionPerformed`.

**Polymorphism** is leveraged through the `ActionListener` interface. All interactive `JButton` objects register an instance of `Main` (which implements `ActionListener`) to handle their events. When a button is clicked, the `actionPerformed` method is invoked, and the specific action is determined based on the `actionCommand` of the event source. This allows a single method to handle diverse events from multiple button objects in a unified manner.

Individual UI components like `JButton` and `JTextField` are themselves **objects** instantiated from their respective classes, demonstrating object creation and interaction. The overall design promotes modularity, reusability, and maintainability, aligning with best practices in object-oriented software development.

## 4. Implementation Details

### 4.1 Graphical User Interface (GUI)
The GUI is constructed using Java Swing components. A `JFrame` provides the main window. A `JTextField` (`displayField`) is used to show input and results, configured for right-alignment and non-editability. A `JPanel` with a `GridLayout` holds all the `JButton`s for numbers, operators, and special functions (Clear, Delete, Equals, History). Custom colors and borders are applied to buttons for better visual distinction, enhancing user experience.

### 4.2 Arithmetic Logic and Flow Control
The calculator's core logic manages `currentInput` (what the user is currently typing), `result` (the accumulated calculation), and `lastOperator`. When a number or decimal point is entered, `currentInput` is updated. Upon an operator press, the `calculate()` method is triggered, performing the pending operation. This method uses a `switch` statement (Objective 1: Flow Control) to apply the correct arithmetic operation based on `lastOperator`. Conditional `if-else` statements handle various scenarios such as preventing multiple decimal points, managing initial input, and resetting the calculator state.

### 4.3 Exception Handling
Robustness is ensured through extensive use of `try-catch` blocks for **exception handling** (Objective 3).
*   **`NumberFormatException`**: This exception is caught when attempting to parse user input from the `displayField` (which is a `String`) into a `double`. If a non-numeric string is encountered (e.g., an empty string or invalid character sequence), an error message is displayed, and the calculator resets, preventing application crashes.
*   **`IOException`**: This exception is handled during file operations. Specifically, when the `appendToHistory` method attempts to write to `history.txt` or when the "History" button attempts to read from it. In case of a file access error (e.g., permissions issues, file not found), an error message is logged to the console (for writing) or displayed via `JOptionPane` (for reading), ensuring the application remains stable despite I/O failures.

### 4.4 File I/O for Calculation History
**File I/O Streams** (Objective 4) are implemented to provide persistence for calculation history. The `appendToHistory(String entry)` method is responsible for writing entries to a text file named `history.txt`. It utilizes `FileWriter` (with `true` for append mode) and `PrintWriter` for efficient and convenient writing of lines to the file. This ensures that each calculation result, along with the operation, is appended to the file without overwriting previous entries.

When the "History" button is clicked, the application reads all lines from `history.txt` using `java.nio.file.Files.readAllLines()` and displays them in a scrollable `JTextArea` within a `JOptionPane`. This allows users to review their past calculations. Both reading and writing operations are wrapped in `try-catch` blocks to handle `IOException` as described above.

## 5. Conclusion
This GUI Calculator project successfully integrates several fundamental Java programming concepts into a practical and interactive application. It effectively demonstrates the application of Object-Oriented Programming principles through inheritance and encapsulation, ensuring a structured and maintainable codebase. Robust exception handling prevents common runtime errors, contributing to the application's stability. Furthermore, the implementation of file I/O streams provides a valuable feature for maintaining a persistent history of calculations. The project serves as a comprehensive demonstration of the concepts covered in the CSE2006 course, showcasing the student's ability to develop a complete, functional Java application with a graphical user interface.