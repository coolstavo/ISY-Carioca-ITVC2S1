import App.BaseTemplate;
import App.ConnectScreen;
import Game.Board;
import Game.IllegalMoveException;
import Zeeslag.Moves;

public class Main {

    public static void main(String[] args) throws IllegalMoveException {

        Board board = new Board(8,8);
        Moves moves = new Moves(board);

        System.out.println(board);

        System.out.println(board);

        System.out.println(moves.CheckMove(0, 0));

        System.out.println(board.getBoard()); 
//
//        moves.PlaceMove(0, 0, "X");
//        moves.PlaceMove(1, 1, "X");
//        moves.PlaceMove(2, 2, "X");

        System.out.println(board);

    }
}
