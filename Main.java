import Game.IllegalMoveException;
import Zeeslag.*;

import java.util.Scanner;
import Server.Connection;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        Connection connection = new Connection();



        String playerName = "marty"; // Replace with the desired username
        connection.login(playerName);

        // Example: Register for the game finder for a valid game type
        String gameType = "Battleship";  // Replace with a valid game type
        connection.SubscribeForGame(gameType);

        // Request the list of online players after logging in
        connection.getPlayerList();
        connection.closeConnection();
        System.out.println("Application exiting...");


        Game game = new Game();
        game.start();

    }
}
