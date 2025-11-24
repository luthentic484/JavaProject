import javax.swing.;
import javax.swing.border.EmptyBorder;
import java.awt.;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends JFrame implements ActionListener {
     Layout Components
    private CardLayout cardLayout;
    private JPanel centerPanel; 
    private JPanel buttonGridPanel;
    private JPanel historyContainerPanel;
    private JPanel historyContentPanel; 
    private JScrollPane historyScrollPane;
    
     Header Components (Main Screen)
    private JLabel expressionLabel;
    private JTextField displayField;
    private ThemeToggleButton themeToggleBtn;
    private JPanel topSection, headerBar;

     Header Components (History Screen)
    private JPanel historyHeaderBar;
    private JLabel historyTitleLabel;
    private CloseButton historyCloseBtn;

     Logic variables
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private String lastOperator = ;
    private boolean newCalculationStarted = true;
    private boolean isDarkMode = false;
    private boolean isHistoryViewActive = false;
    private ListString historyData = new ArrayList(); 

     Button Lists for Theming
    private ListJButton numberButtons = new ArrayList();
    private ListJButton operatorButtons = new ArrayList();
    private ListJButton otherButtons = new ArrayList();

    public Main() {
        setTitle(Java Calculator);
        setSize(360, 540); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

         --- 1. Top Section (Always Visible) ---
        topSection = new JPanel(new BorderLayout());
        
         Header Bar
        headerBar = new JPanel(new BorderLayout());
        headerBar.setBorder(new EmptyBorder(5, 5, 0, 5));
        
        themeToggleBtn = new ThemeToggleButton();
        themeToggleBtn.setPreferredSize(new Dimension(40, 40));
        themeToggleBtn.addActionListener(e - toggleTheme());
        
        expressionLabel = new JLabel( );
        expressionLabel.setFont(new Font(Arial, Font.PLAIN, 16));
        expressionLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerBar.add(themeToggleBtn, BorderLayout.WEST);
        headerBar.add(expressionLabel, BorderLayout.CENTER);
        topSection.add(headerBar, BorderLayout.NORTH);

         Main Display
        displayField = new JTextField(0);
        displayField.setFont(new Font(Arial, Font.BOLD, 45));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBorder(new EmptyBorder(10, 10, 10, 10));
        topSection.add(displayField, BorderLayout.CENTER);

        add(topSection, BorderLayout.NORTH);

         --- 2. Center Section ---
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

         -- View A Calculator Buttons --
        buttonGridPanel = new JPanel(new GridLayout(5, 4, 10, 10)); 
        
        String[] buttonLabels = {
                History, C, DEL, ,
                7, 8, 9, ,
                4, 5, 6, -,
                1, 2, 3, +,
                +-, 0, ., =
        };

        for (String label  buttonLabels) {
            JButton button;

            if (label.equals(History)) {
                button = new HistoryButton(); 
                button.setActionCommand(History); 
            } else {
                button = new RoundedButton(label);
                button.setFont(new Font(Arial, Font.BOLD, 20));
            }
            
            button.addActionListener(this);

            if (C.equals(label)  DEL.equals(label)  History.equals(label)) {
                otherButtons.add(button);
            } else if (.equals(label)  .equals(label)  -.equals(label)  +.equals(label)  =.equals(label)) {
                operatorButtons.add(button);
            } else {
                numberButtons.add(button);
            }
            buttonGridPanel.add(button);
        }

         -- View B History Layer --
        historyContainerPanel = new JPanel(new BorderLayout());
        
         History Header
        historyHeaderBar = new JPanel(new BorderLayout());
        historyHeaderBar.setBorder(new EmptyBorder(0, 5, 10, 5));
        
        historyTitleLabel = new JLabel(History);
        historyTitleLabel.setFont(new Font(Arial, Font.BOLD, 20));
        
        historyCloseBtn = new CloseButton(); 
        historyCloseBtn.setPreferredSize(new Dimension(30, 30));
        historyCloseBtn.addActionListener(e - showCalculator());

        historyHeaderBar.add(historyTitleLabel, BorderLayout.WEST);
        historyHeaderBar.add(historyCloseBtn, BorderLayout.EAST);
        
        historyContainerPanel.add(historyHeaderBar, BorderLayout.NORTH);

         Scrollable List
        historyContentPanel = new JPanel();
        historyContentPanel.setLayout(new BoxLayout(historyContentPanel, BoxLayout.Y_AXIS));
        
        historyScrollPane = new JScrollPane(historyContentPanel);
        historyScrollPane.setBorder(null);
        historyScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        historyContainerPanel.add(historyScrollPane, BorderLayout.CENTER);

        centerPanel.add(buttonGridPanel, CALC);
        centerPanel.add(historyContainerPanel, HIST);

        add(centerPanel, BorderLayout.CENTER);

        loadHistoryFile();
        applyThemeColors();
        
        setVisible(true);
    }

     --- LOGIC ---

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == themeToggleBtn) return;

        String command = e.getActionCommand();

        if (command.equals(History)) {
            showHistory();
            return;
        }

        if ((command.charAt(0) = '0' && command.charAt(0) = '9')  command.equals(.)) {
            if (newCalculationStarted) {
                displayField.setText();
                currentInput.setLength(0);
                newCalculationStarted = false;
            }
            if (command.equals(.) && currentInput.toString().contains(.)) return;
            currentInput.append(command);
            displayField.setText(currentInput.toString());
        } 
        else if (command.matches([+-])) {
            if (!newCalculationStarted && currentInput.length() == 0 && !lastOperator.isEmpty()) {
                lastOperator = command;
                expressionLabel.setText(formatValue(result) +   + lastOperator);
                return;
            }

            if (newCalculationStarted) {
                try {
                    result = Double.parseDouble(displayField.getText());
                } catch (Exception ex) {
                    result = 0;
                }
                lastOperator = command;
                expressionLabel.setText(formatValue(result) +   + lastOperator);
                currentInput.setLength(0);
                return;
            }

            if (currentInput.length()  0) {
                if (!lastOperator.isEmpty()) {
                    calculate(); 
                } else {
                    try {
                        result = Double.parseDouble(currentInput.toString());
                    } catch (Exception ex) {
                        result = 0;
                    }
                }
                lastOperator = command;
                expressionLabel.setText(formatValue(result) +   + lastOperator);
                currentInput.setLength(0);
                newCalculationStarted = true;
            }
        } 
        else if (command.equals(=)) {
            if (!lastOperator.isEmpty()) {
                String oldVal = formatValue(result);
                String op = lastOperator;
                String currentVal = displayField.getText(); 
                
                if(currentInput.length() == 0) currentInput.append(currentVal);
                
                calculate();
                expressionLabel.setText(oldVal +   + op +   + currentVal +  =);
                lastOperator = ;
                newCalculationStarted = true;
            }
        } 
        else if (command.equals(+-)) {
            try {
                double val = Double.parseDouble(displayField.getText())  -1;
                displayField.setText(formatValue(val));
                currentInput.setLength(0);
                currentInput.append(formatValue(val));
            } catch (Exception ex) {}
        } 
        else if (command.equals(C)) resetCalculator();
        else if (command.equals(DEL)) {
            if (currentInput.length()  0 && !newCalculationStarted) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                displayField.setText(currentInput.length() == 0  0  currentInput.toString());
            }
        } 
    }

    private void calculate() {
        if (currentInput.length() == 0 && !newCalculationStarted) return;
        if (lastOperator.isEmpty()) return;

        String valStr = currentInput.length()  0  currentInput.toString()  displayField.getText();
        double currentValue;
        try { currentValue = Double.parseDouble(valStr); } catch (Exception e) { return; }

        String calculationString = formatValue(result) +   + lastOperator +   + formatValue(currentValue) +  =;

        switch (lastOperator) {
            case + result += currentValue; break;
            case - result -= currentValue; break;
            case  result = currentValue; break;
            case 
                if (currentValue == 0) {
                    displayField.setText(Error);
                    resetCalculator();
                    return;
                }
                result = currentValue;
                break;
        }
        
        String resStr = formatValue(result);
        displayField.setText(resStr);
        
        String logEntry = calculationString +   + resStr;
        historyData.add(logEntry);
        updateHistoryFile(); 
    }

    private void resetCalculator() {
        currentInput.setLength(0);
        result = 0;
        lastOperator = ;
        newCalculationStarted = true;
        displayField.setText(0);
        expressionLabel.setText( );
    }

     Decimal Formatting Logic
    private String formatValue(double val) {
        if (Double.isInfinite(val)  Double.isNaN(val)) {
            return Error;
        }
        if (val == (long) val) {
            return String.format(%d, (long) val);
        } else {
             Use DecimalFormat to limit to 10 decimal places
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
             pattern 0 before dot, up to 10 # after dot
            DecimalFormat df = new DecimalFormat(0.##########, symbols);
            return df.format(val);
        }
    }

     --- HISTORY VIEW LOGIC ---

    private void showCalculator() {
        isHistoryViewActive = false;
        cardLayout.show(centerPanel, CALC);
    }

    private void showHistory() {
        isHistoryViewActive = true;
        
        historyContentPanel.removeAll();

        if (historyData.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(isDarkMode  new Color(32, 32, 32)  new Color(240, 243, 245));
            emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            
            JLabel emptyLabel = new JLabel(No history yet);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font(Arial, Font.PLAIN, 16));
            emptyLabel.setForeground(isDarkMode  Color.GRAY  Color.DARK_GRAY);
            
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            historyContentPanel.add(emptyPanel);
        } else {
            for (int i = 0; i  historyData.size(); i++) {
                String entry = historyData.get(i);
                final int index = i;

                JPanel row = new JPanel(new BorderLayout());
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                row.setBorder(new EmptyBorder(5, 5, 5, 5));
                row.setBackground(isDarkMode  new Color(50, 50, 50)  new Color(230, 230, 230));

                JLabel text = new JLabel(entry);
                text.setFont(new Font(Monospaced, Font.PLAIN, 14));
                text.setForeground(isDarkMode  Color.WHITE  Color.BLACK);

                BinButton deleteBtn = new BinButton();
                deleteBtn.setPreferredSize(new Dimension(30, 30));
                deleteBtn.addActionListener(e - deleteHistoryItem(index));

                row.add(text, BorderLayout.CENTER);
                row.add(deleteBtn, BorderLayout.EAST);
                
                historyContentPanel.add(row);
                historyContentPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        historyContentPanel.revalidate();
        historyContentPanel.repaint();
        cardLayout.show(centerPanel, HIST);
    }

    private void deleteHistoryItem(int index) {
        if (index = 0 && index  historyData.size()) {
            historyData.remove(index);
            updateHistoryFile();
            showHistory(); 
        }
    }

    private void loadHistoryFile() {
        File file = new File(history.txt);
        if (file.exists()) {
            try {
                historyData = Files.readAllLines(Paths.get(history.txt));
            } catch (IOException e) {
                historyData = new ArrayList();
            }
        }
    }

    private void updateHistoryFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(history.txt))) {
            for (String line  historyData) {
                out.println(line);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

     --- THEME LOGIC ---

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyThemeColors();
        
        if (isHistoryViewActive) {
            showHistory();
        }
    }

    private void applyThemeColors() {
        Color bgColor, txtColor, btnNumColor;

        if (isDarkMode) {
            bgColor = new Color(32, 32, 32);
            txtColor = new Color(240, 240, 240);
            btnNumColor = new Color(50, 50, 50);
        } else {
            bgColor = new Color(240, 243, 245);
            txtColor = new Color(50, 50, 50);
            btnNumColor = Color.WHITE;
        }

        getContentPane().setBackground(bgColor);
        topSection.setBackground(bgColor);
        headerBar.setBackground(bgColor);
        centerPanel.setBackground(bgColor);
        buttonGridPanel.setBackground(bgColor);
        
        historyContainerPanel.setBackground(bgColor);
        historyHeaderBar.setBackground(bgColor);
        historyContentPanel.setBackground(bgColor);
        historyScrollPane.setBackground(bgColor);
        historyScrollPane.getViewport().setBackground(bgColor);
        
        historyTitleLabel.setForeground(txtColor);
        displayField.setBackground(bgColor);
        displayField.setForeground(txtColor);
        expressionLabel.setForeground(isDarkMode  Color.GRAY  Color.DARK_GRAY);

        for (JButton btn  numberButtons) {
            if (btn instanceof RoundedButton) ((RoundedButton)btn).setColors(btnNumColor, txtColor);
        }

        Color opColor = isDarkMode  new Color(200, 110, 0)  new Color(230, 130, 20);
        Color eqColor = isDarkMode  new Color(30, 100, 30)  new Color(60, 140, 60);
        
        for (JButton btn  operatorButtons) {
            if (btn instanceof RoundedButton) {
                if (btn.getText().equals(=)) ((RoundedButton)btn).setColors(eqColor, Color.WHITE);
                else ((RoundedButton)btn).setColors(opColor, Color.WHITE);
            }
        }

        Color actionColor = isDarkMode  new Color(140, 40, 40)  new Color(180, 60, 60); 
        Color histColor = isDarkMode  new Color(0, 80, 120)  new Color(50, 120, 180); 
        
        for (JButton btn  otherButtons) {
            if (btn instanceof RoundedButton) {
                ((RoundedButton)btn).setColors(actionColor, Color.WHITE);
            }
            if (btn instanceof HistoryButton) {
                ((HistoryButton)btn).setColors(histColor, Color.WHITE);
            }
        }
        
        historyCloseBtn.setColor(txtColor);
        themeToggleBtn.repaint();
    }

     --- CUSTOM COMPONENTS ---

    class CloseButton extends JButton {
        private Color color = Color.BLACK;

        public CloseButton() {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        public void setColor(Color c) {
            this.color = c;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(color);
            g2.setStroke(new BasicStroke(3));
            
            int pad = 8;
            g2.drawLine(pad, pad, getWidth() - pad, getHeight() - pad);
            g2.drawLine(getWidth() - pad, pad, pad, getHeight() - pad);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    class HistoryButton extends JButton {
        private Color normalColor = Color.BLUE;
        private Color hoverColor = Color.CYAN;
        private Color iconColor = Color.WHITE;

        public HistoryButton() {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public void setColors(Color bg, Color icon) {
            this.normalColor = bg;
            this.iconColor = icon;
            this.hoverColor = bg.darker();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) g2.setColor(normalColor.darker());
            else if (getModel().isRollover()) g2.setColor(hoverColor);
            else g2.setColor(normalColor);
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            g2.setColor(iconColor);
            g2.setStroke(new BasicStroke(2));

            int w = getWidth();
            int h = getHeight();
            int size = Math.min(w, h)  2;
            int cx = w  2;
            int cy = h  2;

            g2.drawOval(cx - size2, cy - size2, size, size);
            g2.drawLine(cx, cy, cx, cy - size2 + 4); 
            g2.drawLine(cx, cy, cx - size2 + 4, cy); 
            
            g2.drawLine((int)(cx - size2), cy, (int)(cx - size2 - 3), cy - 3);
            g2.drawLine((int)(cx - size2), cy, (int)(cx - size2 + 3), cy - 3);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    class BinButton extends JButton {
        public BinButton() {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(220, 53, 69));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.setColor(Color.WHITE);
            int w = 12, h = 14;
            int x = (getWidth() - w)  2;
            int y = (getHeight() - h)  2 + 2;
            g2.fillRect(x - 2, y - 2, w + 4, 2);
            g2.fillRect(x + w2 - 2, y - 4, 4, 2);
            g2.fillRect(x, y, w, h);
            g2.setColor(new Color(220, 53, 69));
            g2.fillRect(x + 3, y + 2, 2, h - 4);
            g2.fillRect(x + 7, y + 2, 2, h - 4);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class RoundedButton extends JButton {
        private Color normalColor = Color.WHITE;
        private Color hoverColor = Color.GRAY;
        private Color textColor = Color.BLACK;

        public RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public void setColors(Color bg, Color txt) {
            this.normalColor = bg;
            this.textColor = txt;
            this.hoverColor = bg.equals(Color.WHITE)  new Color(230,230,230)  bg.darker();
            setForeground(txt);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) g2.setColor(normalColor.darker());
            else if (getModel().isRollover()) g2.setColor(hoverColor);
            else g2.setColor(normalColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class ThemeToggleButton extends JButton {
        public ThemeToggleButton() {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int availableSize = Math.min(getWidth(), getHeight());
            int size = (int) ((availableSize - 10)  0.7); 
            int x = (getWidth() - size)  2;
            int y = (getHeight() - size)  2;

            if (isDarkMode) {
                g2.setColor(Color.ORANGE);
                g2.fillOval(x + 2, y + 2, size - 4, size - 4);
                g2.setStroke(new BasicStroke(2));
                for (int i = 0; i  360; i += 45) {
                    double rad = Math.toRadians(i);
                    int x1 = (int) (getWidth()  2 + (size  2 - 1)  Math.cos(rad));
                    int y1 = (int) (getHeight()  2 + (size  2 - 1)  Math.sin(rad));
                    int x2 = (int) (getWidth()  2 + (size  2 + 3)  Math.cos(rad));
                    int y2 = (int) (getHeight()  2 + (size  2 + 3)  Math.sin(rad));
                    g2.drawLine(x1, y1, x2, y2);
                }
            } else {
                g2.setColor(Color.DARK_GRAY);
                g2.fillOval(x, y, size, size);
                g2.setColor(getParent().getBackground()); 
                g2.fillOval(x + size  3, y - 1, size, size);
            }
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() - new Main());
    }
}
