import App.BaseTemplate;
import App.ConnectScreen;
import Game.Board;
import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(8, 8);
        Moves moves = new Moves(board);

        try {
            Ship patrolShip = new Ship("Patrouilleschip");
            Ship submarine = new Ship("Onderzeeër");
            Ship battleship = new Ship("Slagschip");
            Ship aircraftCarrier = new Ship("Vliegdekschip");

            // Place a patrol ship horizontally starting at row 2, column 3
            moves.placeShip(patrolShip, 2, 3, true);

            // Place another patrol ship vertically starting at row 5, column 7

            moves.placeShip(submarine, 3, 4, true);

            moves.placeShip(aircraftCarrier,0,0,true);

            // Display the board after placing ships
            System.out.println(board);

        } catch (ShipNotAvailableException | IllegalMoveException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }



}
