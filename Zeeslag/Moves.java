package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;

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
        if (board.getBoard().get(row).get(column).equals(" ")) {
            return true;
        } else {
            throw new IllegalMoveException("Illegal move: Cell is already occupied" + "(" + row + "," + column + ")");
        }
    }


    //-------------------------------METHODS--------------------------------

    public void placeShip(Ship ship, int startRow, int startColumn, boolean isHorizontal) throws IllegalMoveException {
        int shipLength = ship.getLength();

        // Check if the ship has already been placed on the board, if so, throw an exception
        if (board.isShipAlreadyPlaced(ship)) {
            throw new IllegalMoveException("Ship type already placed " + "(" + ship.getType() + ")" + " You can only place one of each ship type.");
        }
//      checks if placement is valid, if not throws exception.
        if (!IsPlacementValid(startRow, startColumn)) {
            throw new IllegalMoveException("Invalid ship placement " +"("+ ship.getType() + ")");
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

    public boolean IsPlacementValid(int newRow, int newColumn) {
        int numRows = board.getNrOfRows();
        int numColumns = board.getNrOfColumns();

        // Check if the given position is within the board boundaries
        if (newRow < 0 || newRow >= numRows || newColumn < 0 || newColumn >= numColumns) {
            System.out.println("Invalid placement. Please choose another position.");
            return false;
        }

        // Check if there is at least one " " (water) block around the new position, including diagonals
        for (int i = newRow - 1; i <= newRow + 1; i++) {
            for (int j = newColumn - 1; j <= newColumn + 1; j++) {
                // Skip checking outside the board boundaries
                if (i >= 0 && i < numRows && j >= 0 && j < numColumns && !(i == newRow && j == newColumn)) {
                    if (board.getBoard().get(i).get(j).equals(" ")) {
                        // There is at least one water block around the new position
                        return true;
                    }
                }
            }
        }

        // All surrounding positions are occupied, placement is invalid
        System.out.println("Invalid placement. Please choose another position.");
        return false;
    }









//            if (board.getBoard().get(row + 1).get(column).equals(" ")) {
//                if (board.getBoard().get(row - 1).get(column).equals(" ")) {
//                    if (board.getBoard().get(row).get(column - 1).equals(" ")) {
//                        if (board.getBoard().get(row).get(column + 1).equals(" ")) {
//                            if (board.getBoard().get(row - 1).get(column + 1).equals(" ")) {
//                                if (board.getBoard().get(row - 1).get(column - 1).equals(" ")) {
//                                    if (board.getBoard().get(row + 1).get(column + 1).equals(" ")) {
//                                        if (board.getBoard().get(row + 1).get(column - 1).equals(" ")) {
//                                            return true;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            System.out.println("Invalid placement. Please choose another position.");
//            return false;
//        }

//



    
}
