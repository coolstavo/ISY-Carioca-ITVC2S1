package Zeeslag;

import Game.Player;
import Game.IllegalMoveException;
import java.util.Scanner;


public  class ZeeslagPlayer extends Player {

    private Moves playMoves;
    private Moves placeMoves;

    private ZeeslagBoard shipPlacementBoard;
    private ZeeslagBoard playBoard;

    public ZeeslagPlayer(String name, Moves playMoves, Moves placeMoves, ZeeslagBoard shipPlacementBoard, ZeeslagBoard playBoard) {
        super(name);

        this.playMoves = playMoves;
        this.placeMoves = placeMoves;

        this.shipPlacementBoard = shipPlacementBoard;
        this.playBoard = playBoard;
    }

//    public void makeMove() throws IllegalMoveException {
//        boolean validMove = false;
//
//        while (!validMove) {
//            Scanner scanner = new Scanner(System.in);
//
//            // Ask for row coordinate
//            int row;
//            do {
//                System.out.print(getName() + " Enter a row (0 to 7): ");
//                row = scanner.nextInt();
//
//                if (!isValidRow(row)) {
//                    System.out.println("Invalid input for the row. Enter a valid number.");
//                }
//            } while (!isValidRow(row));
//
//            // Validate column coordinate
//            int column;
//            do {
//                System.out.print(getName() + " Enter a column (0 to 7): ");
//                column = scanner.nextInt();
//
//                if (!isValidColumn(column)) {
//                    System.out.println("Invalid input for the column. Enter a valid number.");
//                }
//            } while (!isValidColumn(column));
//
//            // Check if the move is valid (i.e., the cell is empty) and place the move
//            if (!playMoves.checkMove(row, column)) {
//                System.out.println("You already shot here, try again.");
//            } else {
//                playMoves.placeHit(row,column);
//            }
//
//            // Add any extra logic related to the move
//            System.out.println(playBoard);
//            System.out.println("------------------------------------------------");
//
//            validMove = true; // Mark the move as valid to exit the loop
//        }
//    }

    public void makeMoveAgainstOpponent(ZeeslagPlayer opponent) throws IllegalMoveException {
        boolean validMove = false;

        while (!validMove) {
            Scanner scanner = new Scanner(System.in);

            System.out.println(getName()+" playBoard");
            System.out.println(playBoard);
            System.out.println("------------------------------------------------");
            System.out.println(getName()+"s shipBoard");
            System.out.println(shipPlacementBoard);
            System.out.println("------------------------------------------------");

            // Ask for row coordinate
            int row;
            do {
                System.out.print(getName() + " Enter a row (0 to 7): ");
                row = scanner.nextInt();

                if (!isValidRow(row)) {
                    System.out.println("Invalid input for the row. Enter a valid number.");
                }
            } while (!isValidRow(row));

            // Validate column coordinate
            int column;
            do {
                System.out.print(getName() + " Enter a column (0 to 7): ");
                column = scanner.nextInt();

                if (!isValidColumn(column)) {
                    System.out.println("Invalid input for the column. Enter a valid number.");
                }
            } while (!isValidColumn(column));


            // Check the opponent's placement board for a hit or miss
            if (opponent.placeMoves.checkMiss(row, column)) {
                System.out.println("It's a MISS!");
                opponent.placeMoves.placeMove(row, column, Moves.MISS);

                // Update your play board with the result
                playMoves.placeMove(row, column, Moves.MISS);

            } else {
                System.out.println("It's a HIT!");
                opponent.placeMoves.placeHit(row, column);

                // Update your play board with the result
                playMoves.placeMove(row, column, Moves.HIT);
            }

            // Add any extra logic related to the move
//            System.out.println(getName()+" playBoard");
//            System.out.println(playBoard);
//            System.out.println("------------------------------------------------");
//            System.out.println(getName()+"s shipBoard");
//            System.out.println(shipPlacementBoard);
//            System.out.println("------------------------------------------------");


            validMove = true; // Mark the move as valid to exit the loop
        }
    }

    private boolean isValidRow(int row) {
        // Add your validation logic for X-coordinate here
        // For example, you can check if xCoordinate is within a specific range
        //        return (xCoordinate >= 0 && xCoordinate <= ShipPlacementBoard.getNrOfRows() - 1);
        return (row >= 0 && row <= shipPlacementBoard.getNrOfRows() - 1);
    }

    private boolean isValidColumn(int column) {
        // Add your validation logic for Y-coordinate here
        // For example, you can check if yCoordinate is within a specific range
        return (column >= 0 && column <= shipPlacementBoard.getNrOfColumns() - 1);
    }

}