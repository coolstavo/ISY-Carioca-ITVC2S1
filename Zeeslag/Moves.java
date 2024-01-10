package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;

import java.util.ArrayList;

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
            if(IsPlacementValid(ship, startRow, startColumn, true)){
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
            }else {
                System.out.printf("You cannot place an %s here\n", ship.getType());
            }
        } else {
            if(IsPlacementValid(ship, startRow, startColumn, false)){
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
            }else {
                System.out.printf("You cannot place an %s here \n", ship.getType());
            }
        }
    }

    public boolean IsPlacementValid(Ship ship, int row, int column, boolean isHorizontal) {
        if(isNearShip(ship, row, column, isHorizontal)) return false;
        else return true;
    }

    public ArrayList<int[]> getAllNearPositions(int row, int column) {
        ArrayList<int[]> list = new ArrayList<>();
        //up
        if (row - 1 >= 0) list.add(new int[] {row - 1, column});

        //down
        if (row + 1 < board.getNrOfRows()) list.add(new int[]{row + 1, column});

        //right
        if (column + 1 < board.getNrOfColumns()) list.add(new int[]{row, column + 1});

        //left
        if (column - 1 >= 0) list.add(new int[]{row, column - 1});

        //up & right
        if (row - 1 >= 0 && column + 1 < board.getNrOfColumns()) list.add(new int[]{row - 1, column + 1});

        //up & left
        if (row - 1 >= 0 && column - 1 >= 0) list.add(new int[]{row - 1, column - 1});

        //down & right
        if (row + 1 < board.getNrOfRows() && column + 1 < board.getNrOfColumns()) list.add(new int[]{row + 1, column + 1});

        //down & left
        if (row + 1 < board.getNrOfRows() && column - 1 >= 0) list.add(new int[]{row + 1, column - 1});
        return list;
    }

    public boolean isNearShip(Ship ship, int startRow, int startColumn, boolean isHorizontal) {
        // ...
        int k;
        int length = ship.getLength();

        if (isHorizontal) k = startColumn;
        else k = startRow;
        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            if (isShipAround(startRow, startColumn, ship)) return true;

            if (isHorizontal) startColumn++;
            else startRow++;
        }
        return false;
    }

    private boolean isShipAround(int startRow, int startColumn, Ship ship) {
    ArrayList<int[]> list = getAllNearPositions(startRow, startColumn);
    for (int[] position : list) {
        int row = position[0];
        int column = position[1];

        // Check if there is a ship at the specified position
        if (!board.getBoard().get(row).get(column).equals(" ")) {
            return true;
        }
    }
    return false;
}


//         private boolean isShipAround(int startRow, int startColumn){
//             ArrayList<int[]> list = getAllNearPositions(startRow, startColumn);
//             for (int[] position : list){
//                 if(board.getBoard().get(position[0]).equals(" "))return true;
//             }
//             return false;
//         }
    }

