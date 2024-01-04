package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;

public class Moves implements Moveable {

    private Board board;

    public Moves(Board board) {
        this.board = board;
    }

    @Override
    public void placeMove(int row, int column, String piece) {
        board.getBoard().get(row).set(column, piece);  // Set the specified piece on the board
    }

    @Override
    public boolean checkMove(int row, int column) throws IllegalMoveException {
        if (board.getBoard().get(row).get(column).equals(" ")) {
            return true;
        } else {
            throw new IllegalMoveException("Illegal move: Cell is already occupied");
        }
    }

    public void placeShip(Ship ship, int startRow, int startColumn, boolean isHorizontal) throws IllegalMoveException {
        int shipLength = ship.getLength();

        if (isHorizontal) {
            for (int i = startColumn; i < startColumn + shipLength; i++) {
                checkMove(startRow, i);
            }

            for (int i = startColumn; i < startColumn + shipLength; i++) {
                placeMove(startRow, i, ship.getRepresentation());  // Set ship type on the board
            }
        } else {
            for (int i = startRow; i < startRow + shipLength; i++) {
                checkMove(i, startColumn);
            }

            for (int i = startRow; i < startRow + shipLength; i++) {
                placeMove(i, startColumn, ship.getRepresentation()); // Set ship type on the board
            }
        }
        
    }

    public boolean IsPlacementValid(int row, int column) {
        if (board.getBoard().get(row + 1).get(column).equals(" ")) {
            if (board.getBoard().get(row - 1).get(column).equals(" ")) {
                if (board.getBoard().get(row).get(column - 1).equals(" ")) {
                    if (board.getBoard().get(row).get(column + 1).equals(" ")) {
                        if (board.getBoard().get(row - 1).get(column + 1).equals(" ")) {
                            if (board.getBoard().get(row - 1).get(column - 1).equals(" ")) {
                                if (board.getBoard().get(row + 1).get(column + 1).equals(" ")) {
                                    if (board.getBoard().get(row + 1).get(column - 1).equals(" ")) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Invalid placement. Please choose another position.");
        return false;
    }   }








