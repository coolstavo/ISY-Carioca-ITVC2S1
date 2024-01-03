package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;

public class Moves implements Moveable {
    private ZeeslagBoard player1Board;
    private ZeeslagBoard player2Board;
    private int currentPlayer;

    public Moves(ZeeslagBoard player1Board, ZeeslagBoard player2Board) {
        this.player1Board = player1Board;
        this.player2Board = player2Board;
        this.currentPlayer = 1;  // Start with player 1

    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private ZeeslagBoard getCurrentBoard() {
        return (currentPlayer == 1) ? player1Board : player2Board;
    }

    @Override
    public void placeMove(int row, int column, String piece) {
        getCurrentBoard().getBoard().get(row).set(column, piece);  // Set the specified piece on the board
    }

    @Override
    public boolean checkMove(int row, int column) throws IllegalMoveException {
        if (getCurrentBoard().getBoard().get(row).get(column).equals(" ")) {
            return true;
        } else {
            throw new IllegalMoveException("Illegal move: Cell is already occupied");
        }
    }

    public void placeShip(Ship ship, int startRow, int startColumn, boolean isHorizontal) throws IllegalMoveException {
        ZeeslagBoard currentBoard = getCurrentBoard();
        int shipLength = ship.getLength();

        if (isHorizontal) {
            for (int i = startColumn; i < startColumn + shipLength; i++) {
                checkMove(startRow, i);
            }

            for (int i = startColumn; i < startColumn + shipLength; i++) {
                placeMove(startRow, i, ship.getRepresentation());
            }
        } else {
            for (int i = startRow; i < startRow + shipLength; i++) {
                checkMove(i, startColumn);
            }

            for (int i = startRow; i < startRow + shipLength; i++) {
                placeMove(i, startColumn, ship.getRepresentation());
            }
        }
    }
}
