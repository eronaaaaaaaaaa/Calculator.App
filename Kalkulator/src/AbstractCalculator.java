import javax.swing.*;
import java.awt.*;

public abstract class AbstractCalculator implements CalculatorInterface {

    protected static final int WINDOW_WIDTH = 490;
    protected static final int WINDOW_HEIGHT = 600;
    protected static final int BUTTON_WIDTH = 80;
    protected static final int BUTTON_HEIGHT = 70;
    protected static final int MARGIN_X = 20;
    protected static final int MARGIN_Y = 60;

    protected JFrame window;
    protected JTextField inText;
    protected JButton btnDarkMode, btnSwitchToScientificMode, btnEqual, btnRoot, btnPower, btnLog, btnSin, btnCos;

    public AbstractCalculator() {
       
    }

	public void appendToDisplay(String str) {
	
	}
}

