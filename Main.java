import Game.IllegalMoveException;
import Zeeslag.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        Scanner scanner = new Scanner(System.in);

        // Create a ZeeslagBoard
        ZeeslagBoard zeeslagBoard = new ZeeslagBoard(10, 10);

        // Create Moves object
        Moves moves = new Moves(zeeslagBoard);

        int option;
        do {
            System.out.println("1. Place Ship");
            System.out.println("2. Place Hit");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Place Ship
                    System.out.println("Available ship types: Patrouilleschip, Onderzeeër, Slagschip, Vliegdekschip");
                    System.out.print("Enter ship type: ");
                    String shipType = scanner.next();
                    System.out.print("Enter ship start row: ");
                    int startRow = scanner.nextInt();
                    System.out.print("Enter ship start column: ");
                    int startColumn = scanner.nextInt();
                    System.out.print("Enter ship orientation (1 for horizontal, 2 for vertical): ");
                    int orientation = scanner.nextInt();

                    boolean isHorizontal = orientation == 1;

                    try {
                        Ship ship = new Ship(shipType);
                        moves.placeShip(ship, startRow, startColumn, isHorizontal);
                        System.out.println("Ship placed successfully!");

                        // Display the updated board
                        System.out.println(zeeslagBoard);
                    } catch (ShipNotAvailableException | IllegalMoveException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    // Place Hit
                    System.out.print("Enter hit row: ");
                    int hitRow = scanner.nextInt();
                    System.out.print("Enter hit column: ");
                    int hitColumn = scanner.nextInt();

                    try {
                        moves.placeHit(hitRow, hitColumn);
                        System.out.println("Hit placed successfully!");

                        // Display the updated board
                        System.out.println(zeeslagBoard);
                    } catch (IllegalMoveException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    // Exit
                    System.out.println("Exiting the program.");
                    break;

                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        } while (option != 3);

        scanner.close();
    }
}
