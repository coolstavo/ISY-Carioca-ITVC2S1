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

    public Ship placeHit(int row, int column) throws IllegalMoveException {
        // Check if the move is within the board
        if (row < 0 || row >= board.getNrOfRows() || column < 0 || column >= board.getNrOfColumns()) {
            throw new IllegalMoveException("Illegal move: Coordinates are outside the board" + "(" + row + "," + column + ")");
        }

        // Check if the move is a hit or miss
        Ship hitShip = board.getShipAt(row, column);

        if (hitShip != null) {
            placeMove(row, column, HIT);  // Place a hit on the board
            System.out.println("Hit!");
            return hitShip;
        } else {
            placeMove(row, column, MISS);  // Place a miss on the board
            System.out.println("Miss!");
            return null;
        }
    }





}