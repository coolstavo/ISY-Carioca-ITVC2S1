/*
 * SelectScreen - Game selection screen
 *
 * This class provides a graphical user interface for selecting a game to play. It displays a list of available games,
 * with options to view game details and launch the selected game. The UI is built using Swing components.
 *
 * Usage:
 * 1. Instantiate the SelectScreen class.
 * 2. Call the show() method to display the game selection screen.
 *
 * Example:
 *   SelectScreen gameSelectionScreen = new SelectScreen();
 *   gameSelectionScreen.show();
 *
 * Note: The class includes a list of default games (Tic Tac Toe and Dammen) to showcase the functionality.
 */

package App;

import javax.swing.*;
import java.awt.*;

public class SelectScreen extends BaseTemplate {

    // Components for the game selection screen
    private DefaultListModel<Game> gameListModel;
    private JList<Game> gameList;
    private JButton playButton;
    private JButton playGameButton;
    private JLabel label;

    /*
     * Constructor: SelectScreen
     * Creates an instance of the SelectScreen with the title "Select Game."
     */
    public SelectScreen() {
        super("Select Game");
        initializeUI();
        addEventListeners();
    }

    /*
     * Method: initializeUI
     * Initializes the user interface by creating the game list, buttons, and labels.
     */
    private void initializeUI() {
        // Create a DefaultListModel and JList for displaying games
        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);

        // Create a JScrollPane to scroll through the game list
        JScrollPane scrollPane = new JScrollPane(gameList);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        // Create default games for demonstration purposes
        Game game1 = new Game("Tic Tac Toe", "Description 1");
        Game game2 = new Game("Dammen", "Description 2");
        gameListModel.addElement(game1);
        gameListModel.addElement(game2);

        // Create buttons and labels
        playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(0, 50));
        playButton.setBackground(Color.GREEN);

        playGameButton = new JButton("Play Game");
        playGameButton.setPreferredSize(new Dimension(150, 50));

        label = new JLabel("Kies een spel om te spelen");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        // Add components to the panel with specified layout
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.WEST);
        panel.add(playButton, BorderLayout.SOUTH);
        panel.add(playGameButton, BorderLayout.SOUTH);
        panel.setBackground(new Color(0x398743));

        // Set application icon
        frame.setIconImage(new ImageIcon("logo.png").getImage());
    }

    /*
     * Method: addEventListeners
     * Adds action listeners to the play buttons to handle button clicks.
     */
    private void addEventListeners() {
        playButton.addActionListener(e -> handlePlayButtonClick());
        playGameButton.addActionListener(e -> handlePlayGameButtonClick());
    }

    /*
     * Method: handlePlayButtonClick
     * Handles the play button click event by displaying the selected game's name.
     */
    private void handlePlayButtonClick() {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            label.setText("Geselecteerde spel: " + selectedGame.getName());
        } else {
            label.setText("Kies een spel om te spelen");
        }
    }

    /*
     * Method: handlePlayGameButtonClick
     * Handles the play game button click event by launching the selected game or displaying an error message.
     */
    private void handlePlayGameButtonClick() {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            JOptionPane.showMessageDialog(frame, "Launching game: " + selectedGame.getName());
        } else {
            JOptionPane.showMessageDialog(frame, "Select a game to play.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Method: main
     * Entry point for the SelectScreen application. Creates and displays the game selection screen.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the game selection screen
            new SelectScreen().show();
        });
    }
}
