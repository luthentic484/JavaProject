# 🧮 Java GUI Calculator

A robust, modern, and aesthetically pleasing calculator application built entirely using **Java Swing** and **AWT**. This project demonstrates advanced UI customization, file persistence, and state management within a desktop application.

## 🌟 Features

*   **Standard Arithmetic:** Performs addition, subtraction, multiplication, and division with precision.
*   **Smart Logic:** Handles chained calculations (e.g., `5 + 5 * 2`) and allows changing operators on the fly.
*   **Precision Formatting:** Automatically handles decimal places, showing integers when possible and rounding non-terminating decimals to 10 digits.
*   **🌗 Dark & Light Mode:** A built-in theme toggle with custom-drawn Sun and Moon icons. The theme applies instantly to all UI components.
*   **📜 Persistent History:**
    *   Saves calculation history to a local `history.txt` file.
    *   View history via a slide-over panel (using CardLayout).
    *   Delete individual history entries using a custom "Bin" button.
    *   Handles empty history states gracefully.
*   **Custom UI Components:** Uses `Graphics2D` to draw rounded buttons, icons, and hover effects programmatically (no external image assets required).

## 🛠️ Tech Stack

*   **Language:** Java (JDK 8+)
*   **GUI Framework:** Swing (`javax.swing`) & AWT (`java.awt`)
*   **File I/O:** `java.io` & `java.nio` for history management.

## 🚀 How to Run

### Prerequisites
Ensure you have the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) installed on your machine.

### Steps
1.  **Save the file:**
    Save the provided code into a file named **`Main.java`**.

2.  **Compile the code:**
    Open your terminal or command prompt, navigate to the directory where you saved the file, and run:
    ```bash
    javac Main.java
    ```

3.  **Run the application:**
    ```bash
    java Main
    ```

## 📂 Project Structure & Concepts

This project serves as a practical example of Object-Oriented Programming (OOP) and GUI development:

*   **Encapsulation:** All logic and UI components are encapsulated within the `Main` class.
*   **Inner Classes:** Custom components like `RoundedButton`, `ThemeToggleButton`, `HistoryButton`, and `BinButton` are implemented as inner classes to override `paintComponent` for custom rendering.
*   **Layout Managers:**
    *   `BorderLayout`: Used for the main window structure.
    *   `GridLayout`: Used for the calculator keypad.
    *   `CardLayout`: Used to switch between the Calculator view and the History view.
    *   `BoxLayout`: Used for the scrollable history list.
*   **Event Handling:** Implements `ActionListener` to handle button clicks and logical flow control.
*   **File Persistence:** Reads from and appends to a text file to keep history alive even after the app is closed.

## 🎮 Usage Guide

1.  **Theming:** Click the **Sun/Moon icon** in the top-left corner to toggle between Light Mode and Dark Mode.
2.  **Calculation:** Use the keypad or your keyboard (if mapped) to enter numbers.
    *   The **Expression Label** (top right) shows the current operation being performed.
    *   **+/-** toggles positive/negative numbers.
3.  **History:**
    *   Click the **Clock Icon** (blue button) to open the History view.
    *   Click the **Red Bin Icon** next to an entry to delete it.
    *   Click the **X** button at the top right of the history panel to return to the calculator.

## 🤝 Contributing

Feel free to fork this project and submit pull requests. Ideas for improvements:
*   Add keyboard support (KeyListeners).
*   Add scientific calculator functions (sin, cos, tan).
*   Add a clear-all history button.

---

**Author:** [Arrnav Gupta/luthentic484]
**License:** Open Source
