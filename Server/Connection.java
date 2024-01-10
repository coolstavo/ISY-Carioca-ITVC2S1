/*
 * Connection - Handles server connection attempts
 *
 * This class provides methods for attempting a connection to a server using a specified host and port.
 * It uses a Socket to establish the connection and handles exceptions such as Connection refused and IOException.
 *
 * Usage:
 * 1. Instantiate the Connection class, passing the server host and port to the constructor.
 * 2. Call the attemptServerConnection() method to attempt a connection to the server.
 *
 * Example:
 *   Connection connection = new Connection("localhost", 7789);
 *   boolean isConnected = connection.attemptServerConnection();
 *
 * Note: If the connection attempt is successful, the method returns true; otherwise, it returns false.
 */

package Server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Connection {

    // Fields to store server host and port
    private String serverHost;
    private int serverPort;

    /*
     * Constructor: Connection
     * Creates an instance of the Connection with the specified server host and port.
     *
     * Parameters:
     * - serverHost: The host of the server.
     * - serverPort: The port on which the server is listening.
     */
    public Connection(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /*
     * Method: attemptServerConnection
     * Attempts a connection to the server using the specified host and port.
     *
     * Returns:
     * - true if the connection is successful.
     * - false if the connection fails, printing an error message.
     */
    public boolean attemptServerConnection() {
        try {
            // Attempt to establish a socket connection to the server
            Socket socket = new Socket(serverHost, serverPort);
            socket.close();
            return true;
        } catch (ConnectException e) {
            // Handle connection refused exception
            System.err.println("Kan niet verbinden met de server: Connection refused");
            return false;
        } catch (IOException e) {
            // Handle general IOException
            System.err.println("Kan niet verbinden met de server: " + e.getMessage());
            return false;
        }
    }
}
