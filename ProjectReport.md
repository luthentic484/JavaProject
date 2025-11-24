# 📑 Project Report: Java GUI Calculator

**Project Name:** Java GUI Calculator

**Language:** Java (Swing/AWT)

**Author:** Arrnav Gupta / @luthentic484

**Date:** 24/11/2025

---

## 1. 📝 Abstract
This project involves the development of a fully functional, desktop-based Calculator application using the Java programming language. The application utilizes the **Java Swing** framework for the Graphical User Interface (GUI) and **AWT (Abstract Window Toolkit)** for custom graphics rendering. Beyond standard arithmetic, this calculator features a sophisticated Dark/Light theme engine, persistent history tracking using File I/O, and a responsive, modern user interface.

## 2. 🎯 Objectives
The primary objectives of this project were to:
1.  **Implement OOP Principles:** Demonstrate the use of Classes, Inheritance (extending `JFrame`), Encapsulation (private fields), and Polymorphism.
2.  **Master GUI Development:** specific focus on Layout Managers (`BorderLayout`, `GridLayout`, `CardLayout`) and Event Handling (`ActionListener`).
3.  **Manage Application State:** Handle complex logic for chained calculations and operator precedence.
4.  **Implement File Persistence:** Read and write data to a local text file to maintain calculation history across sessions.
5.  **Custom Component Rendering:** Use `java.awt.Graphics2D` to create custom shapes and icons programmatically, removing dependencies on external image files.

## 3. ✨ Key Features

### 3.1. Core Calculation Logic
*   Performs addition, subtraction, multiplication, and division.
*   **Chained Operations:** Allows continuous calculations (e.g., `5 + 5 * 2`) without needing to press "equals" repeatedly.
*   **Smart Formatting:** Results are formatted to display integers where possible, and decimal results are rounded to a maximum of 10 digits to prevent overflow and ensure readability.

### 3.2. Dynamic Theming (Dark/Light Mode)
*   Features a custom toggle button that draws a **Sun** or **Moon** icon based on the current state.
*   Clicking the toggle instantly repaints the entire UI (backgrounds, buttons, text) without restarting the application.
*   Visual consistency is maintained across all panels, including the history view.

### 3.3. Advanced History Management
*   **Overlay View:** The history panel slides over the keypad using a `CardLayout`, keeping the display visible.
*   **Persistence:** Every valid calculation is saved to `history.txt`.
*   **Management:** Users can delete individual entries using a custom **"Bin" icon** button.
*   **Empty State:** Displays a user-friendly "No history yet" message when appropriate.

### 3.4. Modern UI/UX
*   **Rounded Buttons:** All buttons are rendered with smooth, rounded corners.
*   **Hover Effects:** Buttons change color dynamically when hovered over or pressed.
*   **Custom Icons:** The "History", "Bin", and "Theme" icons are drawn using vector math in Java, ensuring they scale perfectly on any screen.

## 4. ⚙️ Technical Implementation

### 4.1. Class Structure
The project is encapsulated within a main class `Main` which extends `JFrame`.
*   **`Main`**: Handles the window setup, file I/O operations, and calculation logic.
*   **Inner Classes**: Used to create custom Swing components:
    *   `RoundedButton`: Overrides `paintComponent` to draw rounded rectangles.
    *   `ThemeToggleButton`: Draws the Sun/Moon graphics.
    *   `HistoryButton`: Draws the Clock/Arrow icon.
    *   `BinButton`: Draws the trash can icon.
    *   `CloseButton`: Draws the 'X' icon for the history header.

### 4.2. Layout Management
*   **BorderLayout**: Used for the main container (Header at Top, Content in Center).
*   **GridLayout (5x4)**: Used for the numeric keypad and operators.
*   **CardLayout**: Crucial for the History feature. It allows the center panel to swap between the "Calculator Grid" layer and the "History List" layer programmatically.
*   **BoxLayout**: Used inside the History ScrollPane to stack history entries vertically.

### 4.3. Exception Handling
*   **File I/O**: `IOException` is handled when reading/writing `history.txt` to prevent crashes if the file is missing or locked.
*   **Math**: Division by zero is detected and displayed as "Error".
*   **Parsing**: `NumberFormatException` is handled to prevent crashes during invalid input sequences.

## 5. 🚀 Challenges & Solutions

| Challenge | Solution |
| :--- | :--- |
| **Invisible Text in Dark Mode** | Default Swing LookAndFeel overrides colors. **Solution:** Removed system LookAndFeel and implemented a manual `applyThemeColors()` method that updates every component explicitly. |
| **Logic&nbsp;Chaining&nbsp;(5&nbsp;+&nbsp;5&nbsp;*)** | Initially, pressing an operator reset the logic. **Solution:** Implemented state checks (`newCalculationStarted`) to distinguish between starting a new number or changing the current operator. |
| **External Assets** | Using image files (png/jpg) for icons makes the project hard to share. **Solution:** Used `Graphics2D` to draw icons (Sun, Moon, Bin, Clock) via code, making the app a single portable file. |
| **Decimal Precision** | Results like `10/3` created infinite decimals. **Solution:** Implemented `DecimalFormat` with the pattern `0.##########` to limit precision to 10 places. |

## 6. 🔮 Future Scope
*   **Keyboard Support:** Implementing `KeyListener` to allow typing numbers with the physical keyboard.
*   **Scientific Functions:** Adding a secondary panel for `sin`, `cos`, `tan`, and `log`.
*   **Database Integration:** replacing the text file with SQLite for more robust data storage.

## 7. 🏁 Conclusion
The Java Calculator project successfully meets all functional requirements. It moves beyond a basic tutorial application by incorporating professional UI patterns, persistent data storage, and robust state management. It serves as a comprehensive demonstration of intermediate Java programming concepts.

---
*End of Report*
