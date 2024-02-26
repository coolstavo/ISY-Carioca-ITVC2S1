import Game.IllegalMoveException;
import Server.Server;
import Zeeslag.ShipNotAvailableException;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws ShipNotAvailableException, IOException, InterruptedException, IllegalMoveException {

        System.out.println("Welcome to the Carioca game server! 🎮");

        System.out.println("--------------------");

        System.out.println("How would you like to connect?");
        System.out.println("1. AI");
        System.out.println("2. Human");

        String choice = input.nextLine();

        if (choice.equals("1")) {
            System.out.println("You have chosen to play as the AI.");


        } else if (choice.equals("2")) {
            System.out.println("You have chosen to play as a human.");
            Server server = Server.getInstance();

        } else {
            System.out.println("Invalid choice. Please try again.");
            System.exit(0);
        }


    }
}
