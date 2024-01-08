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
        if (isHorizontal) {
             for (int i = startColumn; i < startColumn + shipLength; i++) {
                if (!IsPlacementValid(startRow, startColumn, ship)) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");
                }
            }
            // Check if the ship can be placed on the board (horizontal)
            for (int i = startColumn; i < startColumn + shipLength; i++) {

                if (startColumn + ship.getLength() > board.getNrOfColumns()) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");

                } else if (!checkMove(startRow, i)) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");

                } else {
                    placeMove(startRow, i, ship.getRepresentation());  // Set ship type on the board
                }
            }
            board.placeShip(ship);  // Add ship to the list of placed ships


        } else {
            for (int i = startRow; i < startRow + shipLength; i++) {
                if (!IsPlacementValid(startRow, startColumn, ship)) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");
                }
            }
            // Check if the ship can be placed on the board (vertical)
            for (int i = startRow; i < startRow + shipLength; i++) {

                if (startRow + ship.getLength() > board.getNrOfRows()) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");

                } else if (!checkMove(i, startColumn) && startRow + ship.getLength() > 8) {
                    throw new IllegalMoveException("Invalid ship placement " + "(" + ship.getType() + ")");

                } else {
                    placeMove(i, startColumn, ship.getRepresentation()); // Set ship type on the board
                }
            }
            board.placeShip(ship);  // Add ship to the list of placed ships
        }
    }

    public boolean IsPlacementValid(int r, int c, Ship ship) {
        String excluded = ship.getRepresentation();
        boolean result = false;
        if (c>=0 && c<=board.getNrOfColumns()){
            if (r>=0 && r<=board.getNrOfRows()){
                if (c==0){
                    if (r==0){
                        if (    board.getBoard().get(r).get(c).equals(" ") || board.getBoard().get(r).get(c).equals(excluded)
                                && board.getBoard().get(r+1).get(c).equals(" ") || board.getBoard().get(r+1).get(c).equals(excluded)
                                && board.getBoard().get(r+1).get(c+1).equals(" " ) || board.getBoard().get(r+1).get(c+1).equals(excluded)
                                && board.getBoard().get(r).get(c+1).equals(" ") || board.getBoard().get(r).get(c+1).equals(excluded)
                        ){
                            result = true;
                        }
                    } else if (r>=1 && r < board.getNrOfRows()) {
                        if (    board.getBoard().get(r).get(c).equals(" ") || board.getBoard().get(r).get(c).equals(excluded)
                                && board.getBoard().get(r+1).get(c).equals(" ") || board.getBoard().get(r+1).get(c).equals(excluded)
                                && board.getBoard().get(r+1).get(c+1).equals(" " ) || board.getBoard().get(r+1).get(c+1).equals(excluded)
                                && board.getBoard().get(r).get(c+1).equals(" ") || board.getBoard().get(r).get(c+1).equals(excluded)
                                && board.getBoard().get(r-1).get(c+1).equals(" ") || board.getBoard().get(r-1).get(c+1).equals(excluded)
                                && board.getBoard().get(r-1).get(c).equals(" ") || board.getBoard().get(r-1).get(c-1).equals(excluded)
                        ){
                            result = true;
                        }
                    } else if (r== board.getNrOfRows()) {
                        if (    board.getBoard().get(r).get(c).equals(" ") || board.getBoard().get(r).get(c).equals(excluded)
                                && board.getBoard().get(r).get(c+1).equals(" ") || board.getBoard().get(r).get(c+1).equals(excluded)
                                && board.getBoard().get(r-1).get(c+1).equals(" ") || board.getBoard().get(r-1).get(c+1).equals(excluded)
                                && board.getBoard().get(r-1).get(c).equals(" ") || board.getBoard().get(r-1).get(c-1).equals(excluded)
                        ){
                            result = true;
                        }
                    }
                }
                if (c== board.getNrOfColumns()){

               }
        }


        }
        return result;
    }

}
