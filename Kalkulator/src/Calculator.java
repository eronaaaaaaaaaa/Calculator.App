import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class Calculator extends AbstractCalculator {
    private char opt = ' ';
    private boolean go = true;
    private boolean addWrite = true;
    private double val = 0;
    private boolean isScientificMode = false;
    private boolean isDarkMode = false;

    public Calculator() {
        super();
        initUI();
    }

    public void appendToDisplay(String str) {
        repaintFont();
        if (addWrite) {
            if (!inText.getText().equals("0")) {
                inText.setText(inText.getText() + str);
            } else {
                inText.setText(str);
            }
        } else {
            inText.setText(str);
            addWrite = true;
        }
        go = true;
    }

    public void repaintFont() {
        inText.setFont(new Font("Segoe UI", Font.ROMAN_BASELINE, 27));
    }

    public void updateDisplay(double val) {
        inText.setText(Double.toString(val));
    }

    public void updateDisplay(String val) {
        inText.setText(val);
    }

    public void showError(String message) {
        inText.setText(message);
        inText.setFont(new Font("Calibri MS", Font.PLAIN, 28));
        inText.setForeground(Color.black);
    }

    public double calc(double x1, String input, char opt) {
        double x2 = Double.parseDouble(input);
        switch (opt) {
            case '+':
                return x1 + x2;
            case '-':
                return x1 - x2;
            case '*':
                return x1 * x2;
            case '/':
                return x1 / x2;
            case '%':
                return x1 % x2;
            default:
                return x2;
        }
    }

    private void initUI() {
        window = new JFrame("Calculator");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setResizable(false);

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 27);
        Font smallTxtBtnFont = new Font("Segoe UI", Font.PLAIN, 27);

        createTopButtons();
        createMainButtons(btnFont);
        createScientificButtons(btnFont, smallTxtBtnFont);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createTopButtons() {
        btnSwitchToScientificMode = createTopButton("Scientific Mode", 45, event -> onShowScientificMode());
        btnDarkMode = createTopButton("Dark Mode", 270, event -> toggleDarkMode());
    }

    private JButton createTopButton(String text, int x, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, 25, 140, 25);
        button.setBackground(Color.darkGray);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);
        window.add(button);
        return button;
    }

    private void createMainButtons(Font btnFont) {
        int[] x = {MARGIN_X, MARGIN_X + 90, 200, 290, 380};
        int[] y = {MARGIN_Y, MARGIN_Y + 90, MARGIN_Y + 180, MARGIN_Y + 260, MARGIN_Y + 340, MARGIN_Y + 420};

        inText = new JTextField("0");
        inText.setBounds(x[0], y[0], 440, 80);
        inText.setEditable(false);
        inText.setBackground(Color.WHITE);
        inText.setFont(new Font("Segoe UI", Font.LAYOUT_NO_LIMIT_CONTEXT, 30));
        window.add(inText);

        createMainButton("C", x[0], y[1], btnFont, event -> resetCalculator());
        createMainButton("<-", x[1], y[1], btnFont, event -> backspace());
        createMainButton("%", x[2], y[1], btnFont, event -> handleOperator('%'));
        createMainButton("/", x[3], y[1], btnFont, event -> handleOperator('/'));
        createMainButton("7", x[0], y[2], btnFont, event -> appendToDisplay("7"));
        createMainButton("8", x[1], y[2], btnFont, event -> appendToDisplay("8"));
        createMainButton("9", x[2], y[2], btnFont, event -> appendToDisplay("9"));
        createMainButton("*", x[3], y[2], btnFont, event -> handleOperator('*'));
        createMainButton("4", x[0], y[3], btnFont, event -> appendToDisplay("4"));
        createMainButton("5", x[1], y[3], btnFont, event -> appendToDisplay("5"));
        createMainButton("6", x[2], y[3], btnFont, event -> appendToDisplay("6"));
        createMainButton("-", x[3], y[3], btnFont, event -> handleOperator('-'));
        createMainButton("1", x[0], y[4], btnFont, event -> appendToDisplay("1"));
        createMainButton("2", x[1], y[4], btnFont, event -> appendToDisplay("2"));
        createMainButton("3", x[2], y[4], btnFont, event -> appendToDisplay("3"));
        createMainButton("+", x[3], y[4], btnFont, event -> handleOperator('+'));
        createMainButton(".", x[0], y[5], btnFont, event -> appendDecimal());
        createMainButton("0", x[1], y[5], btnFont, event -> appendToDisplay("0"));

        btnEqual = createMainButton("=", x[2], y[5], btnFont, event -> calculateResult());
        btnEqual.setSize(BUTTON_WIDTH * 2 + 10, BUTTON_HEIGHT);
    }

    private JButton createMainButton(String text, int x, int y, Font font, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setFont(font);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        window.add(button);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        window.getContentPane().setBackground(new Color(230, 230, 230));

        Color lightOrange = new Color(255, 200, 100);
        if ("C = + % / * - <-".contains(text)) {
            button.setBackground(lightOrange);
            button.setForeground(Color.BLACK);
        }
        return button;
    }

    private void createScientificButtons(Font btnFont, Font smallTxtBtnFont) {
        int[] x = {MARGIN_X, MARGIN_X + 90, 200, 290, 380};
        int[] y = {MARGIN_Y, MARGIN_Y + 90, MARGIN_Y + 180, MARGIN_Y + 260, MARGIN_Y + 340, MARGIN_Y + 420};

        btnRoot = createScientificButton("√", x[4], y[1], btnFont, event -> sqrt());
        btnPower = createScientificButton("x^2", x[4], y[2], smallTxtBtnFont, event -> square());
        btnLog = createScientificButton("log", x[4], y[3], smallTxtBtnFont, event -> log());
        btnSin = createScientificButton("sin", x[4], y[4], smallTxtBtnFont, event -> sin());
        btnCos = createScientificButton("cos", x[4], y[5], smallTxtBtnFont, event -> cos());
    }

    private JButton createScientificButton(String text, int x, int y, Font font, java.awt.event.ActionListener actionListener) {
        JButton button = createMainButton(text, x, y, font, actionListener);
        button.setVisible(false);

        if ("√ x^2 log sin cos".contains(text)) {
            button.setBackground(Color.GRAY);
            button.setForeground(Color.WHITE);
        }
        return button;
    }

    private void resetCalculator() {
        repaintFont();
        inText.setText("0");
        opt = ' ';
        val = 0;
    }

    private void backspace() {
        repaintFont();
        String str = inText.getText();
        StringBuilder str2 = new StringBuilder();
        for (int i = 0; i < (str.length() - 1); i++) {
            str2.append(str.charAt(i));
        }
        inText.setText(str2.toString().isEmpty() ? "0" : str2.toString());
    }

    private void handleOperator(char operator) {
        repaintFont();
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()) && go) {
            val = calc(val, inText.getText(), opt);
            updateDisplay(val);
            opt = operator;
            go = false;
            addWrite = false;
        } else {
            opt = operator;
        }
    }

    private void appendDecimal() {
        repaintFont();
        if (addWrite) {
            inText.setText(inText.getText() + ".");
        } else {
            inText.setText("0.");
            addWrite = true;
        }
        go = true;
    }

    private void calculateResult() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()) && go) {
            val = calc(val, inText.getText(), opt);
            updateDisplay(val);
            opt = '=';
            addWrite = false;
        }
    }

    private void sqrt() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            double value = Double.parseDouble(inText.getText());
            inText.setText(value < 0 ? Math.sqrt(Math.abs(value)) + "i" : String.valueOf(Math.sqrt(value)));
        }
    }

    private void square() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            double value = Double.parseDouble(inText.getText());
            inText.setText(String.valueOf(Math.pow(value, 2)));
        }
    }

    private void log() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            double value = Double.parseDouble(inText.getText());
            inText.setText(value <= 0 ? "Undefined" : String.valueOf(Math.log10(value)));
        }
    }

    private void sin() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            double value = Double.parseDouble(inText.getText());
            inText.setText(String.valueOf(Math.sin(Math.toRadians(value))));
        }
    }

    private void cos() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            double value = Double.parseDouble(inText.getText());
            inText.setText(String.valueOf(Math.cos(Math.toRadians(value))));
        }
    }

    private void onShowScientificMode() {
        isScientificMode = !isScientificMode;
        boolean isVisible = isScientificMode;
        btnRoot.setVisible(isVisible);
        btnPower.setVisible(isVisible);
        btnLog.setVisible(isVisible);
        btnSin.setVisible(isVisible);
        btnCos.setVisible(isVisible);
        btnSwitchToScientificMode.setText(isScientificMode ? "Basic Mode" : "Scientific Mode");
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            setDarkMode();
        } else {
            setLightMode();
        }
    }

    private void setDarkMode() {
        window.getContentPane().setBackground(Color.darkGray);
        inText.setBackground(Color.darkGray);
        inText.setForeground(Color.WHITE);
        btnDarkMode.setText("Light Mode");
    }

    private void setLightMode() {
        window.getContentPane().setBackground(new Color(230, 230, 230));
        inText.setBackground(Color.WHITE);
        inText.setForeground(Color.BLACK);
        btnDarkMode.setText("Dark Mode");
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
