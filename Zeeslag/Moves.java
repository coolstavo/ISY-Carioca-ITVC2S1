package Zeeslag;

import Game.Moveable;
import Game.Board;
import Game.IllegalMoveException;

public class Moves implements Moveable {

    Board board;
    public Moves(Board board){
        this.board = board;

    }

    @Override
    public void PlaceMove(int row, int column, String piece) {
        board.getBoard().get(row).set(column, piece);

    }

    @Override
    public boolean CheckMove(int row, int column) throws IllegalMoveException {

        if (board.getBoard().get(row).get(column).equals(" ")){
            return true;
        } else {
            throw new IllegalMoveException("Illegal move");
        }
    }
}
