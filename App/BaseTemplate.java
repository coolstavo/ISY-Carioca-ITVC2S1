package App;

import javax.swing.*;

public abstract class BaseTemplate {
    //
    JFrame frame;
    JPanel panel;
    String title;

    public BaseTemplate(String title) {

        // Create a new JFrame and JPanel
        frame = new JFrame(title);
        panel = new JPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public void show() {
        // Make the frame visible when the show() method is called
        frame.setVisible(true);
    }

    public void hide() {
        // Hide the frame
        frame.setVisible(false);
    }
}
