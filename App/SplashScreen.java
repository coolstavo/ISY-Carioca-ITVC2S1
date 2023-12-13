/*
 * SplashScreen - A simple splash screen for application loading
 *
 * This class provides a splash screen with an image, a loading status label, and a progress bar.
 * The splash screen is displayed for a specified duration before automatically hiding itself.
 *
 * Usage:
 * 1. Instantiate the SplashScreen class, passing a title to the constructor.
 * 2. Call the show() method to display the splash screen with the default loading message.
 *
 * Example:
 *   SplashScreen splashScreen = new SplashScreen("Loading Screen");
 *   splashScreen.show();
 *
 * Note: The default duration for which the splash screen will be visible is set to 3000 milliseconds (3 seconds).
 * The image used in the splash screen is loaded from the specified file path.
 */

package App;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends BaseTemplate {

    // Duration for which the splash screen will be visible (in milliseconds)
    private static final int SPLASH_DURATION = 3000;

    // JLabel to display loading status
    private JLabel statusLabel;

    // JProgressBar for indicating loading progress
    private JProgressBar progressBar;

    // Timer for controlling the duration of the splash screen
    private Timer timer;

    /*
     * Constructor: SplashScreen
     * Creates an instance of the SplashScreen with the specified title.
     *
     * Parameters:
     * - title: The title of the SplashScreen window.
     */
    public SplashScreen(String title) {
        super(title);
        initializeSplashScreen();
    }

    /*
     * Method: initializeSplashScreen
     * Initializes the splash screen by setting up the UI and starting the timer.
     */
    private void initializeSplashScreen() {
        setupUI();
        startTimer();
    }

    /*
     * Method: setupUI
     * Sets up the UI components of the splash screen, including the image, status label, and progress bar.
     */
    private void setupUI() {
        // Load image from the specified file path
        ImageIcon image = new ImageIcon("C:\\Users\\Mbhst\\Desktop\\ISY-Carioca-ITVC2S1\\App\\resources\\SplashScreenPhoto.png");

        // Set the layout of the panel
        panel.setLayout(new BorderLayout());

        // Add image to the center of the panel
        panel.add(new JLabel("", image, 0), BorderLayout.CENTER);

        // Create and configure the status label
        statusLabel = new JLabel("Loading application...");
        statusLabel.setBackground(Color.GREEN);
        statusLabel.setOpaque(true);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add status label to the bottom of the panel
        panel.add(statusLabel, BorderLayout.SOUTH);

        // Create and configure the progress bar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(Color.DARK_GRAY);

        // Add progress bar to the top of the panel
        panel.add(progressBar, BorderLayout.NORTH);
    }

    /*
     * Method: startTimer
     * Starts a timer to control the duration of the splash screen.
     * The splash screen will be automatically hidden after the specified duration.
     */
    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hide(); // Hide the splash screen
                timer.cancel(); // Cancel the timer
            }
        }, SPLASH_DURATION);
    }

    /*
     * Method: main
     * Entry point for the SplashScreen application. Creates and displays the splash screen.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splashScreen = new SplashScreen("Loading Screen");
            splashScreen.show();
        });
    }
}
