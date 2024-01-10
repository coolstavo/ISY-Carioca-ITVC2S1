/*
 * ConnectScreen - GUI for establishing a connection to the server
 *
 * This class provides a graphical user interface for connecting to a server using a specified host and port.
 * It includes fields for entering the server host and port, a button to initiate the connection, and a status
 * label to display the connection result. The UI is built using Swing components.
 *
 * Usage:
 * 1. Instantiate the ConnectScreen class, passing a title to the constructor.
 * 2. Call the show() method to display the server connection screen.
 *
 * Example:
 *   ConnectScreen serverConnectScreen = new ConnectScreen("Server Connection");
 *   serverConnectScreen.show();
 *
 * Note: The default host is set to "localhost" and the default port is set to "7789".
 */

package App;

import Server.Connection;
import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

public class ConnectScreen extends BaseTemplate {

    // JLabel to display connection status
    private JLabel connectionStatusLabel;
    private JTextField hostField;
    private JTextField portField;

    /*
     * Constructor: ConnectScreen
     * Creates an instance of the ConnectScreen with the specified title.
     *
     * Parameters:
     * - title: The title of the ConnectScreen window.
     */
    public ConnectScreen(String title) {
        super(title);
        initializeUI();
    }

    /*
     * Method: initializeUI
     * Initializes the user interface by creating the main panel and status button panel.
     */
    private void initializeUI() {
        createMainPanel();
//        createStatusButtonPanel();
    }

    /*
     * Method: createMainPanel
     * Creates the main panel with header label and connection status panel.
     */
    private void createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createHeaderLabel(), BorderLayout.NORTH);
        mainPanel.add(createConnectionStatusPanel(), BorderLayout.CENTER);

        panel.add(mainPanel);
    }

    /*
     * Method: createHeaderLabel
     * Creates and returns the header label for the ConnectScreen.
     */
    private JLabel createHeaderLabel() {
        JLabel textLabel = new JLabel("Welkom bij Carioca AI");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 25)); // Set font size to 25px
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return textLabel;
    }

    /*
     * Method: createConnectionStatusPanel
     * Creates and returns the connection status panel with status label, host field, port field, and connect button.
     */
    private JPanel createConnectionStatusPanel() {
        JPanel connectionStatusPanel = new JPanel();
        connectionStatusPanel.add(createStatusLabel());
        hostField = createHostField();
        portField = createPortField();
        connectionStatusPanel.add(hostField);
        connectionStatusPanel.add(portField);
        connectionStatusPanel.add(createConnectButton());
        return connectionStatusPanel;
    }


    /*
     * Method: createStatusLabel
     * Creates and returns the status label for displaying connection status.
     */
    private JLabel createStatusLabel() {
        connectionStatusLabel = new JLabel("Verbinding maken met de server...");
        return connectionStatusLabel;
    }

    /*
     * Method: createHostField
     * Creates and returns the host text field with a default value of "localhost".
     */
    private JTextField createHostField() {
        return new JTextField("localhost", 15);
    }

    /*
     * Method: createPortField
     * Creates and returns the port text field with a default value of "7789".
     */
    private JTextField createPortField() {
        return new JTextField("7789", 5);
    }

    /*
     * Method: createConnectButton
     * Creates and returns the connect button with an action listener to handle button clicks.
     */
    private JButton createConnectButton() {
        JButton connectionStatusButton = new JButton("Verbinden");
        connectionStatusButton.addActionListener(e -> handleConnectButtonClick());
        return connectionStatusButton;
    }

    /*
     * Method: handleConnectButtonClick
     * Handles the connect button click event by attempting a server connection and updating the status label.
     */
    private void handleConnectButtonClick() {
        String serverHost = hostField.getText();
        try {
            int serverPort = Integer.parseInt(portField.getText());
            Connection serverConnect = new Connection(serverHost, serverPort);

            if (serverConnect.attemptServerConnection()) {
                connectionStatusLabel.setText("Verbonden met de server!");
            } else {
                connectionStatusLabel.setText("Kan niet verbinden met de server: Connection refused");
            }
        } catch (NumberFormatException ex) {
            connectionStatusLabel.setText("Ongeldige poort. Voer een geldig getal in.");
        }
    }

}
