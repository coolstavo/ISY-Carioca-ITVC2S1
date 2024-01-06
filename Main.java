import Game.Board;
import Game.IllegalMoveException;
import Zeeslag.ZeeslagBoard;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        ZeeslagBoard board = new ZeeslagBoard(8, 12);
        Moves moves = new Moves(board);
        Ship P = new Ship("Patrouilleschip");
        Ship O = new Ship("Onderzeeër");
        Ship S = new Ship("Slagschip");
        Ship V = new Ship("Vliegdekschip");

        moves.placeShip(P, 2, 0, true);
        moves.placeShip(O, 1, 0, true);

        // Check if the placement is valid for the given position
        boolean isValidPlacement = moves.IsPlacementValid(1, 0);

        // Use the result of the validation
        if (isValidPlacement) {
            System.out.println("Placement is valid.");
        } else {
            System.out.println("Placement is invalid. Please choose another position.");
        }

        System.out.println(board.getBoard());
        System.out.println(board);
        System.out.println();
        System.out.println(board.getPlacedShips());
    }
}






