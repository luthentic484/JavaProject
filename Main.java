import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Objective 2: OOP (Classes, Objects, Inheritance, Encapsulation)
// The 'Main' class inherits from JFrame, demonstrating inheritance. It encapsulates all UI components and calculator logic.
public class Main extends JFrame implements ActionListener {
    private JTextField displayField; // Objective 2: displayField is an object of JTextField
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private String lastOperator = "";
    private boolean newCalculationStarted = true;

    // Constructor for the Calculator GUI
    public Main() {
        // Objective 2: Object instantiation and encapsulation
        setTitle("CSE2006 - Happy Hogan's Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window

        // Display field
        displayField = new JTextField("0");
        displayField.setFont(new Font("Arial", Font.BOLD, 30));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBackground(Color.LIGHT_GRAY);
        add(displayField, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns, 10px spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttonLabels = {
                "C", "/", "*", "DEL",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", ".",
                "0", "History", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(this); // Objective 2: ActionListener object attached to buttons
            
            // Apply different colors for operators and special buttons
            switch (label) { // Objective 1: Flow Control (switch statement)
                case "=":
                    button.setBackground(new Color(0, 150, 0)); // Green
                    button.setForeground(Color.WHITE);
                    break;
                case "+": case "-": case "*": case "/":
                    button.setBackground(new Color(255, 165, 0)); // Orange
                    button.setForeground(Color.WHITE);
                    break;
                case "C": case "DEL":
                    button.setBackground(new Color(200, 0, 0)); // Red
                    button.setForeground(Color.WHITE);
                    break;
                case "History":
                    button.setBackground(new Color(0, 100, 200)); // Blue
                    button.setForeground(Color.WHITE);
                    break;
                default:
                    button.setBackground(Color.DARK_GRAY);
                    button.setForeground(Color.WHITE);
                    break;
            }
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Objective 3: GUI Event Dispatching (part of multithreading concept for responsive UIs)
        String command = e.getActionCommand();

        // Objective 1: Flow Control (if-else statements)
        if (command.matches("[0-9]\\]?.")) { // Number or decimal point
            if (newCalculationStarted || displayField.getText().equals("0")) {
                currentInput.setLength(0);
                displayField.setText("");
                newCalculationStarted = false;
            }
            if (command.equals(".") && currentInput.toString().contains(".")) {
                return; // Prevent multiple decimal points
            }
            currentInput.append(command);
            displayField.setText(currentInput.toString());
        } else if (command.matches("[+\-*/]")) { // Operator
            if (currentInput.length() > 0 && !newCalculationStarted) {
                calculate();
            }
            lastOperator = command;
            try { // Objective 3: Exception Handling (try-catch for NumberFormatException)
                result = Double.parseDouble(displayField.getText());
            } catch (NumberFormatException ex) {
                displayField.setText("Error");
                appendToHistory("Error: Invalid number format for operator input.");
                resetCalculator();
                return;
            }
            currentInput.setLength(0);
            newCalculationStarted = true;
        } else if (command.equals("=")) {
            if (currentInput.length() > 0 && !lastOperator.isEmpty()) {
                calculate();
                lastOperator = ""; // Reset operator after calculation
                newCalculationStarted = true;
            }
        } else if (command.equals("C")) { // Clear button
            resetCalculator();
        } else if (command.equals("DEL")) { // Delete last character
            if (currentInput.length() > 0 && !newCalculationStarted) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                displayField.setText(currentInput.length() == 0 ? "0" : currentInput.toString());
            } else if (displayField.getText().length() > 1 && newCalculationStarted) {
                displayField.setText(displayField.getText().substring(0, displayField.getText().length() - 1));
            } else {
                displayField.setText("0");
                currentInput.setLength(0);
            }
        } else if (command.equals("History")) {
            try {
                // Display history (simple implementation using JOptionPane)
                java.util.List<String> historyEntries = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("history.txt"));
                if (historyEntries.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "History is empty.", "Calculation History", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder historyContent = new StringBuilder();
                    for (String entry : historyEntries) {
                        historyContent.append(entry).append("\n");
                    }
                    JTextArea historyArea = new JTextArea(historyContent.toString());
                    historyArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(historyArea);
                    scrollPane.setPreferredSize(new Dimension(300, 200));
                    JOptionPane.showMessageDialog(this, scrollPane, "Calculation History", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (IOException ex) { // Objective 3: Exception Handling (try-catch for IOException)
                JOptionPane.showMessageDialog(this, "Error reading history file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                appendToHistory("Error: Failed to read history file.");
            }
        }
    }

    private void calculate() {
        double currentValue;
        try { // Objective 3: Exception Handling (try-catch for NumberFormatException)
            currentValue = Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException ex) {
            displayField.setText("Error");
            appendToHistory("Error: Invalid number format during calculation.");
            resetCalculator();
            return;
        }

        String calculationString = result + " " + lastOperator + " " + currentValue + " =";

        // Objective 1: Flow Control (if-else if statements)
        switch (lastOperator) {
            case "+":
                result += currentValue;
                break;
            case "-":
                result -= currentValue;
                break;
            case "*":
                result *= currentValue;
                break;
            case "/":
                if (currentValue == 0) {
                    displayField.setText("Error: Divide by Zero");
                    appendToHistory(calculationString + " Error: Divide by Zero");
                    resetCalculator();
                    return;
                }
                result /= currentValue;
                break;
            default:
                // If no operator or first number input, just take the current value
                result = currentValue;
                break;
        }
        displayField.setText(String.valueOf(result));
        appendToHistory(calculationString + " " + result);
    }

    private void resetCalculator() {
        currentInput.setLength(0);
        result = 0;
        lastOperator = "";
        newCalculationStarted = true;
        displayField.setText("0");
    }

    // Objective 4: I/O Streams (Writing data to a file)
    private void appendToHistory(String entry) {
        try (PrintWriter out = new PrintWriter(new FileWriter("history.txt", true))) { // 'true' for append mode
            out.println(entry);
        } catch (IOException e) { // Objective 3: Exception Handling (try-catch for IOException)
            System.err.println("Error writing to history file: " + e.getMessage());
            // Optionally, show a message to the user here too, but silently logging is often enough for history.
        }
    }

    public static void main(String[] args) {
        // Objective 3: GUI operations should be run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new Main());
    }
}
