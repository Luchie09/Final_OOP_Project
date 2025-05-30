/*Base class for layout the game frame */
import javax.swing.*;

public abstract class BaseGameFrame extends JFrame {

    public BaseGameFrame(String title) {
        setTitle(title);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // You can add common UI setup methods here if needed in the future
}
