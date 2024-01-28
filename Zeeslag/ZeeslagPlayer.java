package Zeeslag;

import Game.Player;
import Game.IllegalMoveException;

import java.util.Objects;
import java.util.Scanner;

public  class ZeeslagPlayer extends Player {

    private Moves playMoves;
    Moves placeMoves;

    private ZeeslagBoard shipPlacementBoard;
    private ZeeslagBoard playBoard;

    //--------------------------------CONSTRUCTOR--------------------------------

    public ZeeslagPlayer(String name, Moves playMoves, Moves placeMoves, ZeeslagBoard shipPlacementBoard, ZeeslagBoard playBoard) {
        super(name);

        this.playMoves = playMoves;
        this.placeMoves = placeMoves;

        this.shipPlacementBoard = shipPlacementBoard;
        this.playBoard = playBoard;
    }

    //-------------------------------METHODS--------------------------------

    public void makeMoveAgainstOpponent(ZeeslagPlayer opponent) throws IllegalMoveException {
        boolean validMove = false;

        while (!validMove) {
            Scanner scanner = new Scanner(System.in);

            System.out.println(getName()+"'s playBoard");
            System.out.println(playBoard);
            System.out.println("------------------------------------------------");
            System.out.println(getName()+"'s shipBoard");
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


            if (Objects.equals(opponent.shipPlacementBoard.getPiece(row, column), "V") ||
                    Objects.equals(opponent.shipPlacementBoard.getPiece(row, column), "S") ||
                    Objects.equals(opponent.shipPlacementBoard.getPiece(row, column), "M") ||
                    Objects.equals(opponent.shipPlacementBoard.getPiece(row, column), "P")) {
                placeMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
            } else {
                placeMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
            }

            validMove = true;
        }
    }
    //-------------------------------VALIDATION--------------------------------

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