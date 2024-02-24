package Server;

import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;
import Zeeslag.ZeeslagBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    //--------------CONNECTION DETAILS---------------
    static String hostName = "localhost";
    static int portNumber = 7789;

    //----------------SINGLETON----------------------
    private volatile static Server INSTANCE;

    //--------------SOCKET DETAILS-------------------
    private static Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;
    public BufferedReader stdIn;

    //-----------------SCANNER-----------------------
    static Scanner input = new Scanner(System.in);

    //-----------------USER DETAILS-------------------
    String username = null;
    String opponent = null;

    //-----------------RESPONSES----------------------
    private String serverResponse;
    private Map<String, String> serverResponseMap = new HashMap<>();

    //-----------------BOARDS AND SHIPS-------------------------
    private ZeeslagBoard placementBoard = new ZeeslagBoard(8,8);
    private Moves placementMoves = new Moves(placementBoard);

    private ZeeslagBoard gameBoard = new ZeeslagBoard(8,8);
    private Moves gameMoves = new Moves(gameBoard);

    private Ship V = new Ship(Ship.VLIEGDEKSCHIP);
    private Ship S = new Ship(Ship.SLAGSCHIP);
    private Ship M = new Ship(Ship.MIJNENJAGER);
    private Ship P = new Ship(Ship.PATROUILLESCHIP);

    //-------------------BOOLEANS---------------------
    private boolean boatPlaced = false;
    private boolean moveMade = false;
    private boolean loggedIn = false;
    private boolean subscribed = false;


    //----------------------------------------------SINGLETON----------------------------------------------------------

    private Server() throws ShipNotAvailableException {
        client();
    }

    public static Server getInstance() throws IOException, InterruptedException, ShipNotAvailableException {
        if (INSTANCE == null) {
            synchronized (Server.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Server();
                }
            }
        }
        return INSTANCE;
    }

    //----------------------------------------------CONNECTION---------------------------------------------------------

    public void connect(String hostName, int portNumber) throws IOException {
        echoSocket = new Socket(hostName, portNumber);
        out = new PrintWriter(echoSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    //------------------------------------------------SEND-------------------------------------------------------------

    public void sendCommand(String command) throws IOException {
        out.println(command);
    }

    //---------------------------------------------START-----------------------------------------------------------

    public void startServer() throws InterruptedException {

        try {
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);

                if (serverResponse.equals("(C) Copyright 2023 Hanzehogeschool Groningen")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------COMMANDS------------------------------------------------------------
    public void login() throws IOException {

        String response = null;

        while (true) {
            System.out.println("Enter your username: ");
            username = input.nextLine();

            sendCommand("login " + username);

            try {
                response = in.readLine();
            } catch (IOException e) {
                // Handle exception
            }

            if (response == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Handle exception
                }
                continue;
            }

            String[] responseParts = response.split(" ");
            if (responseParts[0].equals("OK")) {
                loggedIn = true;
                break;
            } else if (responseParts[0].equals("ERR")) {
                System.out.println("Error from server: " + response);
            }
        }
    }

    public void subscribe() throws IOException, IllegalMoveException {

        String response = null;

        System.out.println("What would you like to play?");
        System.out.println("---------------------------------");
        System.out.println("1 - Battleship");
        System.out.println("2 - Tic-Tac-Toe");

        while (true) {

            String game = input.nextLine();

            if (game.equals("1")) {
                out.println("subscribe battleship");

            } else if (game.equals("2")) {
                out.println("subscribe tic-tac-toe");
            }

            try {
                response = in.readLine();
            } catch (IOException e) {
                // Handle exception
            }

            if (response == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Handle exception
                }
                continue;
            }

            String[] responseParts = response.split(" ");
            if (responseParts[0].equals("OK")) {
                subscribed = true;
                System.out.println("Waiting for a match...");
                waiting();


            } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("MATCH")) {
                System.out.println("Match found!");

                Map<String, String> responseMap = parseResponseToMap(response);

                opponent = responseMap.get("OPPONENT");
                System.out.println("Match found with " + opponent + "!");
                System.out.println("---------------------------------");
                System.out.println("Waiting for the opponent to place their boats...");

                subscribed = true;

                match();


            } else if (responseParts[0].equals("ERR")) {
                System.out.println("Error from server: " + response);
                // If an error is received, prompt for a new game
                continue;
            }
        }
    }

    public void waiting() throws IOException, IllegalMoveException {

        String response = null;

        System.out.println("Waiting for the opponent...");
        System.out.println("---------------------------------");

        while (true) {
            try {
                response = in.readLine();
            } catch (IOException e) {
                // Handle exception
            }

            if (response == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Handle exception
                }
                continue;
            }

            String[] responseParts = response.split(" ");

            boolean gameEnded = false;
            if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("MATCH")) {
                System.out.println("Match found!");

                Map<String, String> responseMap = parseResponseToMap(response);

                opponent = responseMap.get("OPPONENT");
                System.out.println("Match found with " + opponent + "!");

                System.out.println("---------------------------------");

                match();

            } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("MOVE")) {

                Map<String, String> responseMap = parseResponseToMap(response);

                String move = responseMap.get("MOVE");
                String result = responseMap.get("RESULT");

                String piece;
                if (result.equals("BOEM") || result.equals("GEZONKEN")) {
                    piece = Moves.HIT;
                } else {
                    piece = Moves.MISS;
                }

                receiveMove(move, piece);


            } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("WIN")) {
                System.out.println("You won!");
                gameEnded = true;
                subscribed = false;

                System.out.println("---------------------------------");
                System.out.println("Waiting for a new game...");
                subscribe();
                break;

            } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("LOSS")) {
                System.out.println("You lost!");
                gameEnded = true;
                subscribed = false;

                System.out.println("---------------------------------");
                System.out.println("Waiting for a new game...");
                subscribe();
                break;

            } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("YOURTURN")) {

                System.out.println("It's your turn!");
                move();

            } else if (responseParts[0].equals("ERR")) {
                System.out.println("Error from server: " + response);
                // If an error is received, prompt for a new game
                continue;
            }

        }
    }

    public void match() throws IOException, IllegalMoveException {
        String response = null;

        while (true) {
            try {
                response = in.readLine();
            } catch (IOException e) {
                // Handle exception
            }

            if (response == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Handle exception
                }
                continue;
            }

            String[] responseParts = response.split(" ");

            if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME")) {

                if (responseParts[2].equals("YOURTURN")) {
                    if (!boatPlaced) {
                        System.out.println("It's your turn!");
                        System.out.println("Placing the boats...");
                        System.out.println("---------------------------------");
                        place();

                    } else {
                        System.out.println("Lets play!");
                        move();
                    }
                }

            } else if (responseParts[0].equals("ERR")) {
                System.out.println("Error from server: " + response);
                // If an error is received, prompt for a new game
                continue;
            }
        }
    }

    public void place() throws IOException, IllegalMoveException {
        String response = null;
        int[] boatLengths = {6, 4, 3, 2}; // lengths of the boats

        printBoard(placementBoard);

        for (int length : boatLengths) {
            boatPlaced = false; // Reset boatPlaced to false at the start of each loop
            while (!boatPlaced) {
                System.out.println("Where do you want to place your " + length + "-long boat? (Start and end row and column)");

                System.out.println("Enter start row and column (0-7) separated by a comma: ");
                String startInput = input.nextLine();
                System.out.println("Enter end row and column (0-7) separated by a comma: ");
                String endInput = input.nextLine();

                // Validate the input
                String[] startIndices = startInput.split(",");
                String[] endIndices = endInput.split(",");

                if (startIndices.length != 2 || endIndices.length != 2) {
                    System.out.println("Please enter two indices separated by a comma.");
                    continue;
                }

                try {
                    int startRow = Integer.parseInt(startIndices[0]);
                    int startColumn = Integer.parseInt(startIndices[1]);
                    int endRow = Integer.parseInt(endIndices[0]);
                    int endColumn = Integer.parseInt(endIndices[1]);

                    if (startRow < 0 || startRow > 7 || startColumn < 0 || startColumn > 7 || endRow < 0 || endRow > 7 || endColumn < 0 || endColumn > 7) {
                        System.out.println("Rows and columns must be between 0 and 7.");
                        continue;
                    }

                    // Translate the row and column to an index
                    int startIndex = translateToIndex(startRow, startColumn);
                    int endIndex = translateToIndex(endRow, endColumn);

                    // Send the command to the server with the translated index
                    sendCommand("place " + startIndex + " " + endIndex);

                    try {
                        response = in.readLine();
                    } catch (IOException e) {
                        // Handle exception
                    }

                    if (response == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // Handle exception
                        }
                        continue;
                    }

                    String[] responseParts = response.split(" ");

                    if (responseParts[0].equals("OK")) {

                        if (length == 6) {
                            placeShip(startIndex, V, true);
                        } else if (length == 4) {
                            placeShip(startIndex, S, true);
                        } else if (length == 3) {
                            placeShip(startIndex, M, true);
                        } else if (length == 2) {
                            placeShip(startIndex, P, true);
                        }

                        System.out.println("---------------------------------");
                        boatPlaced = true; // Set boatPlaced to true when a boat is successfully placed

                    } else if (responseParts[0].equals("ERR")) {
                        System.out.println("Error from server: " + response);
                        // If an error is received, prompt for a new game
                        continue;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Indices must be integers.");
                    continue;
                }
            }
        }

        System.out.println("All boats placed!");
        System.out.println("Waiting for the opponent to place their boats...");
        System.out.println("---------------------------------");

        boatPlaced = true;
        match();
    }

    public void move() throws IOException, IllegalMoveException {

        String response = null;
        printBoard(placementBoard);
        printBoard(gameBoard);

        while (!moveMade) {

            moveMade = false; // Reset moveMade to false at the start of each loop

            System.out.println("Where do you want to shoot? (Enter row and column 0-7 separated by a comma)");

            String input = this.input.nextLine();

            // Validate the input
            String[] indices = input.split(",");

            if (indices.length != 2) {
                System.out.println("Please enter two indices separated by a comma.");
                continue;
            }

            try {
                int row = Integer.parseInt(indices[0]);
                int column = Integer.parseInt(indices[1]);

                if (row < 0 || row > 7 || column < 0 || column > 7) {
                    System.out.println("Rows and columns must be between 0 and 7.");
                    continue;
                }

                // Translate the row and column to an index
                int index = translateToIndex(row, column);

                // Send the command to the server with the translated index
                sendCommand("move " + index);

                try {
                    response = in.readLine();
                } catch (IOException e) {
                    // Handle exception
                }

                if (response == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // Handle exception
                    }
                    continue;
                }

                String[] responseParts = response.split(" ");

                if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("MOVE")) {
                    Map<String, String> responseMap = parseResponseToMap(response);

                    String result = responseMap.get("RESULT");
                    String length = responseMap.get("LENGTH");

                    String response1 = in.readLine();

                    if (response1.equals("OK")) {

                        if (result.equals("GEZONKEN")) {
                            System.out.println("Ship with length " + length + ", has been sunk!");
                        } else {
                            System.out.println("Move made at " + row + "," + column + " it was a " + result);
                        }

                        String piece;
                        if (result.equals("BOEM") || result.equals("GEZONKEN")) {
                            piece = Moves.HIT;
                        } else {
                            piece = Moves.MISS;
                        }

                        makeMove(index, piece);
                        waiting();

                        moveMade = true;
                    }

                } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("LOSS")) {
                    System.out.println("You lost!");
                    subscribed = false;

                    System.out.println("---------------------------------");
                    System.out.println("Waiting for a new game...");
                    subscribe();
                    break;

                } else if (responseParts[0].equals("SVR") && responseParts[1].equals("GAME") && responseParts[2].equals("WIN")) {
                    System.out.println("You won!");
                    subscribed = false;

                    System.out.println("---------------------------------");
                    System.out.println("Waiting for a new game...");
                    subscribe();
                    break;

                } else {
                    System.out.println("Error from server: " + response);
                    // If an error is received, prompt for a new game
                    continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("Indices must be integers.");
                continue;
            }
        }
    }

    public void receiveMove(String move, String piece) throws IOException, IllegalMoveException {

        int index = Integer.parseInt(move);
        int[] rowColumn = translateToRowColumn(index);
        int row = rowColumn[0];
        int column = rowColumn[1];

        placementMoves.placeMoveBasicServer(row, column, piece);

        waiting();
    }

    //-------------------------------------TODO----------------------------------------------

    //TODO add functionality for receiving a forfeit from the opponent
    public void forfeit(){

        try {
            sendCommand("forfeit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------TODO----------------------------------------------


    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (stdIn != null) stdIn.close();
        if (echoSocket != null) echoSocket.close();
    }

    //------------------------------------------BOARD METHODS-----------------------------------------------------------

    public void printBoard(ZeeslagBoard board){
        System.out.println(board);
    }


    //TODO add functionality for placing a ship VERTICALLY  on the board
    public void placeShip(int index, Ship shipType, boolean isHorizontal) throws IllegalMoveException {
        // Translate the index to row and column
        int[] rowColumn = translateToRowColumn(index);

        int startRow = rowColumn[0];
        int startColumn = rowColumn[1];

        // Place the ship at the translated location
        placementMoves.placeShip(shipType, startRow, startColumn, isHorizontal);
        System.out.println(placementBoard);
    }


    public void makeMove(int index, String piece) {
        // Translate the index to row and column
        int[] rowColumn = translateToRowColumn(index);

        int row = rowColumn[0];
        int column = rowColumn[1];

        // Make a move at the translated location
        gameMoves.placeMoveBasicServer(row, column, piece);

        // Print the board
        printBoard(placementBoard);
        printBoard(gameBoard);

    }


    //-------------------------------------------------TRANSLATE-----------------------------------------------------------

    public Map<String, String> parseResponseToMap(String response) {
        // Create a HashMap to store the key-value pairs
        Map<String, String> responseMap = new HashMap<>();

        // Extract the substring containing key-value pairs
        String pairsString = response.substring(response.indexOf("{") + 1, response.indexOf("}"));

        // Split the pairsString by "," to get individual key-value pairs
        String[] keyValuePairs = pairsString.split(",");

        // Iterate over key-value pairs
        for (String pair : keyValuePairs) {
            // Split each pair by ":" to separate key and value
            String[] keyValue = pair.trim().split(":");
            // Remove the double quotes from the key and value
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            // Put key-value pair into the map
            responseMap.put(key, value);
        }

        return responseMap;
    }

    public static int[] translateToRowColumn(int index) {
        int row = index / 8; // Integer division gives the row
        int col = index % 8; // Modulo operation gives the column

        return new int[]{row, col};
    }

    public static int translateToIndex(int row, int col) {
        int index = row * 8 + col; // Multiply the row by 8 and add the column to get the index
        return index;
    }


    //------------------------------------------------CLIENT-----------------------------------------------------------

    public void client() {

        try {
            System.out.println("Connecting to the server...");
            connect(hostName, portNumber);
            startServer();

            Thread.sleep(1000);

            while (!loggedIn) {
                login();
            }

            while (!subscribed) {
                subscribe();
            }


        } catch (IOException e) {
            System.out.println("An IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException | IllegalMoveException e) {
            throw new RuntimeException(e);
        }
    }

}