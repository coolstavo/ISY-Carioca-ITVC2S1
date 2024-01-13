package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * The `Connection` class handles the communication between the client and the game server.
 * It manages the connection establishment, sending commands to the server, and handling server responses.
 */
public class Connection {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connectionClosed = false;
    private Map<ServerEvents, List<DataEventListener>> listeners = new HashMap<>();

    /**
     * Attempts to establish a connection with the game server.
     *
     * @return True if the connection is successfully established, false otherwise.
     */
    public boolean attemptServerConnection() {
        try {
            socket = new Socket("localhost", 7789);  // Replace with your server details
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to the server!");
            startServerMessagesThread();
            return true;
        } catch (IOException e) {
            System.err.println("Cannot connect to the server: " + e.getMessage());
            return false;
        }
    }

    /**
     * Starts a separate thread to continuously listen for server messages.
     * The method runs in the background and calls `handleServerResponse` upon receiving a message.
     */
    private void startServerMessagesThread() {
        Thread serverMessages = new Thread(() -> {
            try {
                String response;
                while (!connectionClosed && (response = in.readLine()) != null) {
                    handleServerResponse(response);
                }
            } catch (IOException e) {
                if (!connectionClosed) {
                    e.printStackTrace();
                }
            } finally {
                closeConnection();
            }
        });
        serverMessages.setDaemon(true);
        serverMessages.start();
    }

    /**
     * Sends a command to the server and handles the corresponding server response.
     *
     * @param command The command to be sent to the server.
     */
    public void sendCommand(String command) {
        System.out.println("Sending command: " + command);
        out.println(command);
    }

    /**
     * Handles the server response based on the response type.
     * Calls corresponding event listeners for specific response types.
     *
     * @param response The server response string.
     */
    private void handleServerResponse(String response) {
        System.out.println("Received response: " + response);

        if (response.startsWith("OK")) {
            fireEvent(ServerEvents.OK, null);
        } else if (response.startsWith("ERR")) {
            String reason = response.substring(4);
            fireEvent(ServerEvents.ERROR, reason);
        } else if (response.startsWith("SVR PLAYERLIST")) {
            Map<String, String> data = parsePlayerList(response);
            String playerList = data.get("LIST");
            System.out.println("Received player list: " + playerList);
            // Handle the player list as needed
        } else {
            // Handle other response types based on your needs
        }
    }


    /**
     * Registers the client for the game finder to play a game with someone.
     *
     * @param gameType The type of the game to register for (e.g., "tic-tac-toe , Battleship").
     */
    public void SubscribeForGame(String gameType) {
        sendCommand("subscribe " + gameType);

        try {
            String response;
            while ((response = in.readLine()) != null) {
                handleServerResponse(response);

                if (response.startsWith("OK")) {
                    System.out.println("Registered for game type " + gameType);
                    break;  // Exit the loop after successful registration
                } else {
                    // Handle other responses if needed
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the list of online players from the server.
     * Handles the player list response separately and prints it to the console.
     */
    public void getPlayerList() {
        sendCommand("get playerlist");

        try {
            long timeoutMillis = 5000;  // Set a timeout (5 seconds, adjust as needed)
            long startTime = System.currentTimeMillis();

            String response;
            while ((response = in.readLine()) != null) {
                handleServerResponse(response);

                if (response.startsWith("SVR PLAYERLIST")) {
                    Map<String, String> data = parsePlayerList(response);
                    String playerList = data.get("LIST");
                    System.out.println("Received player list: " + playerList);
                    break;

                    // Handle the player list as needed
                }

                // Check if the timeout has elapsed
                if (System.currentTimeMillis() - startTime > timeoutMillis) {
                    System.out.println("Timeout reached. Exiting getPlayerList loop.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Parses the player list from the server response.
     *
     * @param response The server response containing the player list.
     * @return A map containing the parsed player list.
     */
    private Map<String, String> parsePlayerList(String response) {
        try {
            String[] playerArray = response.substring(15).replaceAll("[\"\\[\\]]", "").split(", ");
            HashMap<String, String> data = new HashMap<>();
            data.put("LIST", String.join(", ", playerArray));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    /**
     * Logs in the client with the specified player name.
     *
     * @param playerName The desired username for the client.
     * @return True if the login is successful, false otherwise.
     */
    public boolean login(String playerName) {
        if (attemptServerConnection()) {
            sendCommand("login " + playerName);
            return true;
        } else {
            System.err.println("Failed to connect to the server. Please check the server status.");
            return false;
        }
    }

    /**
     * Fires a data event to notify registered listeners.
     *
     * @param type The type of the event.
     * @param data Additional data associated with the event.
     */
    private void fireEvent(ServerEvents type, String data) {
        HashMap<String, String> eventData = new HashMap<>();
        if (data != null) {
            eventData.put("DATA", data);
        }
        DataEvent event = new DataEvent(this, eventData, type);
        notifyListeners(type, event);
    }

    /**
     * Notifies registered listeners for a specific event type.
     *
     * @param type  The type of the event.
     * @param event The data event to be sent to listeners.
     */
    private void notifyListeners(ServerEvents type, DataEvent event) {
        if (listeners.containsKey(type)) {
            listeners.get(type).forEach(listener -> listener.data(event));
        }
    }

    /**
     * Closes the connection with the server.
     */
    public void closeConnection() {
        connectionClosed = true;

        try {
            // Send a logout command before closing the connection
            sendCommand("logout");

            // Close the streams and socket
            in.close();
            out.close();
            socket.close();

            System.out.println("Connection closed.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




}
