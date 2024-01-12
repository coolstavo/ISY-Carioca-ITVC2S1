package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;
import Game.*;
public class Moves implements Moveable {


    public final static String HIT = "X";
    public final static String MISS = "O";


    private ZeeslagBoard board;

    //--------------------------------CONSTRUCTOR--------------------------------

    public Moves(Board board) {
        this.board = (ZeeslagBoard) board;
    }

    //-------------------------------OVERRIDES--------------------------------

    @Override
    public void placeMove(int row, int column, String piece) {
        board.getBoard().get(row).set(column, piece);  // Set the specified piece on the board
    }


    @Override
    public boolean checkMove(int row, int column) throws IllegalMoveException {
        // Check if the move is within the board
        if (row >= 0 && row < board.getNrOfRows() && column >= 0 && column < board.getNrOfColumns() &&
                board.getBoard().get(row).get(column).equals(" ")) {
            return true;
        } else {
            System.out.println("Illegal move: Cell is already occupied or out of bounds" + "(" + row + "," + column + ")");
            return false;
        }
    }


    //-------------------------------METHODS--------------------------------

    public void placeShip(Ship ship, int startRow, int startColumn, boolean isHorizontal) throws IllegalMoveException {
        int shipLength = ship.getLength();

        // Check if the ship has already been placed on the board, if so, throw an exception
        if (board.isShipAlreadyPlaced(ship)) {
            throw new IllegalMoveException("Ship type already placed " + "(" + ship.getType() + ")" + " You can only place one of each ship type.");
        }

        if (isHorizontal) {

            // Check if the ship can be placed on the board (horizontal)
            for (int i = startColumn; i < startColumn + shipLength; i++) {

                if (startColumn + ship.getLength() > board.getNrOfColumns()) {
                    throw new IllegalMoveException("Invalid ship placement " +"("+ ship.getType() + ")");

                } else if (!checkMove(startRow, i)) {
                    throw new IllegalMoveException("Invalid ship placement " +"("+ ship.getType() + ")");

                } else {
                    placeMove(startRow, i, ship.getRepresentation());  // Set ship type on the board
                }
            }
            board.placeShip(ship);  // Add ship to the list of placed ships


        } else {

            // Check if the ship can be placed on the board (vertical)
            for (int i = startRow; i < startRow + shipLength; i++) {

                if (startRow + ship.getLength() > board.getNrOfRows()){
                    throw new IllegalMoveException("Invalid ship placement " +"("+ ship.getType() + ")");

                } else if (!checkMove(i, startColumn) && startRow + ship.getLength() > 8) {
                    throw new IllegalMoveException("Invalid ship placement " +"("+ ship.getType() + ")");

                } else {
                    placeMove(i, startColumn, ship.getRepresentation()); // Set ship type on the board
                }
            }
            board.placeShip(ship);  // Add ship to the list of placed ships
        }
    }


    public boolean checkMiss(int row, int column) throws IllegalMoveException {

        // Check if move is withing the board
        if (board.getBoard().get(row).get(column).equals(" ")) {
            return true;
        } else {
            return false;
        }

    }


    public void placeHit(int row, int column) throws IllegalMoveException {
        // Check if the cell is occupied by a ship
        if (!board.getBoard().get(row).get(column).equals(" ")) {
            // Place a hit on the board if the cell is occupied by a ship
            placeMove(row, column, HIT);


            // Check if the ship is sunk
            for (Ship ship : board.getPlacedShips()) {
                if (board.isShipSunk(ship)) {
                    System.out.println("Ship " + ship.getType() + " has been sunk!");

                    // Mark the surrounding area with miss marks
                    markSurroundingArea(ship, row, column);
                }
            }
        } else {
            // Place a miss on the board
            placeMove(row, column, MISS);

        }
    }

    private void markSurroundingArea(Ship ship, int hitRow, int hitColumn) throws IllegalMoveException {
        int shipLength = ship.getLength();

        // Check if the ship is fully sunk
        if (board.isShipSunk(ship)) {

            // Infer the ship's orientation based on the ship's length
            boolean isHorizontal;
            // Check adjacent cells to determine the ship's orientation
            if (hitColumn > 0 && board.getBoard().get(hitRow).get(hitColumn - 1).equals(ship.getRepresentation())) {
                // There is a cell to the left, so it's horizontal
                isHorizontal = true;
            } else if (hitColumn + 1 < board.getNrOfColumns() && board.getBoard().get(hitRow).get(hitColumn + 1).equals(ship.getRepresentation())) {
                // There is a cell to the right, so it's horizontal
                isHorizontal = true;
            } else {}
            isHorizontal = false;


            // Initialize rowOffsets and colOffsets with empty arrays
            int[] rowOffsets = {};
            int[] colOffsets = {};

            if (isHorizontal) {
                // Custom offsets for horizontal ship types
                if (ship.getType().equals("Patrouilleschip")) {
                    rowOffsets = new int[]{-1, -1, -1, -1, 1, 1, 1, 1, 0, 0};
                    colOffsets = new int[]{-1, 0, 1, -shipLength, -1, 0, 1, -shipLength, shipLength - 1, -shipLength};
                } else if (ship.getType().equals("Mijnenjager")) {
                    rowOffsets = new int[]{-1,-1, -1, -1, -1,0, 0, +1,+1, +1, +1, +1};
                    colOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1};
                } else if (ship.getType().equals("Slagschip")) {
                    rowOffsets = new int[]{-1,-1, -1, -1, -1,-1,0, 0, +1,+1, +1, +1, +1,+1};
                    colOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1,-shipLength +1 +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1, -shipLength +1 +1,};
                } else if (ship.getType().equals("Vliegdekschip")) {
                    rowOffsets = new int[]{-1,-1, -1, -1, -1,-1,0, 0, +1,+1, +1, +1, +1,+1, +1,+1,-1,-1};
                    colOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1,-shipLength +1 +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1, -shipLength +1 +1,-shipLength/2 +1, -shipLength/2, -shipLength/2 +1, -shipLength/2};
                }
            } else if(!isHorizontal) {
                // Custom offsets for vertical ship types
                if (ship.getType().equals("Patrouilleschip")) {
                    rowOffsets = new int[]{-1, 0, 1, -shipLength, -1, 0, 1, -shipLength, shipLength - 1, -shipLength};
                    colOffsets = new int[]{-1, -1, -1, -1, 1, 1, 1, 1, 0, 0};
                }  else if (ship.getType().equals("Mijnenjager")) {
                    rowOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1};
                    colOffsets = new int[]{-1,-1, -1, -1, -1,0, 0, +1,+1, +1, +1, +1};
                } else if (ship.getType().equals("Slagschip")) {
                    rowOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1,-shipLength +1 +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1, -shipLength +1 +1,};
                    colOffsets = new int[]{-1,-1, -1, -1, -1,-1,0, 0, +1,+1, +1, +1, +1,+1};
                } else if (ship.getType().equals("Vliegdekschip")) {
                    rowOffsets = new int[]{-shipLength,-1, 0, +1, -shipLength +1,-shipLength +1 +1, +1, -shipLength, -shipLength,-1, 0, +1, -shipLength +1, -shipLength +1 +1,-shipLength/2 +1, -shipLength/2, -shipLength/2 +1, -shipLength/2};
                    colOffsets = new int[]{-1,-1, -1, -1, -1,-1,0, 0, +1,+1, +1, +1, +1,+1, +1,+1,-1,-1};;
                }
            }

            // Iterate over the offsets
            for (int i = 0; i < rowOffsets.length; i++) {
                int row = hitRow + rowOffsets[i];
                int column = hitColumn + colOffsets[i];

                // Check if the coordinates are within the board boundaries using checkMove
                if (checkMove(row, column)) {
                    // Skip the cell where the ship was hit
                    if (!(row == hitRow && column == hitColumn) && !board.getBoard().get(row).get(column).equals(HIT)) {
                        placeMove(row, column, MISS);
                    }
                }
            }
        }
    }



}


//
//        if (!board.getBoard().get(row).get(column).equals(" ")) {
//            // Place a hit on the board if the cell is occupied by a ship
//            placeMove(row, column, HIT);
////            System.out.println("It's a Hit!");
//
//            // Check if the ship is sunk
//            for (Ship ship : board.getPlacedShips()) {
//                if (board.isShipSunk(ship)) {
//                    System.out.println("Ship " + ship.getType() + " has been sunk!");
//                }
//            }
//        } else {
//            // Place a miss on the board
//            placeMove(row, column, MISS);
////            System.out.println("It's a Miss!");
//        }
//    }
//  }
//}