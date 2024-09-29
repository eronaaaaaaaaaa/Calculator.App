public interface CalculatorInterface {
    void appendToDisplay(String str);
    void repaintFont();
    void updateDisplay(double val);
    void updateDisplay(String val);
    void showError(String message);
}
