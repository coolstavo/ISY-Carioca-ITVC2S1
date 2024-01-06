import Game.IllegalMoveException;
import Zeeslag.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        Scanner scanner = new Scanner(System.in);


        ZeeslagBoard board1 = new ZeeslagBoard(10, 10);

        Moves moves = new Moves(board1);



        try {
            moves.placeShip(new Ship("Patrouilleschip"), 0, 0, true);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        try {
            moves.placeShip(new Ship("Onderzeeër"), 2, 3, false);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        System.out.println("Player 1's Board:\n" + board1);


        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Place a hit");
            System.out.println("2. Place a ship");
            System.out.println("3. Exit the game");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter coordinates for hitting (e.g., A3): ");
                    String hitCoordinates = scanner.next().toUpperCase();
                    int hitRow = hitCoordinates.charAt(0) - 'A';
                    int hitColumn = Integer.parseInt(hitCoordinates.substring(1)) - 1;

                    moves.placeHit(hitRow, hitColumn);
                    System.out.println("Player 1's Board:\n" + board1);
                    break;
                case 2:
                    System.out.println("Enter ship details for placing:");

                    System.out.print("Ship type (Patrouilleschip, Onderzeeër, Slagschip, Vliegdekschip): ");
                    String shipType = scanner.next();

                    System.out.print("Starting row (0-9): ");
                    int shipRow = scanner.nextInt();

                    System.out.print("Starting column (0-9): ");
                    int shipColumn = scanner.nextInt();

                    System.out.print("Horizontal (true/false): ");
                    boolean isHorizontal = scanner.nextBoolean();

                    moves.placeShip(new Ship(shipType), shipRow, shipColumn, isHorizontal);
                    System.out.println("Player 1's Board:\n" + board1);
                    break;
                case 3:
                    System.out.println("Exiting the game.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
