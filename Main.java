import Game.IllegalMoveException;
import Zeeslag.ZeeslagBoard;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        ZeeslagBoard board = new ZeeslagBoard(8, 8);
        Moves moves = new Moves(board);
        Ship P = new Ship("Patrouilleschip");
        Ship M = new Ship("Mijnenjager");
        Ship S = new Ship("Slagschip");
        Ship V = new Ship("Vliegdekschip");

        moves.placeShip(P, 2, 0, true);
        moves.placeShip(M, 1, 0, true);
        moves.placeShip(S,0,0, false);
        moves.placeShip(V,7,0,true);


        System.out.println(board);
        System.out.println();
        System.out.println(board.getPlacedShips());
    }
}






