package App;
import javax.swing.*;

public class BaseTemplate {

    //
    JFrame frame;
    JPanel panel;

    public BaseTemplate(String title) {

        // Create a new JFrame and JPanel
        frame = new JFrame(title);
        panel = new JPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void hide() {
        SwingUtilities.invokeLater(() -> frame.setVisible(false));
    }
}
