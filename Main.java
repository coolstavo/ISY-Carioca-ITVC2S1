import Game.Board;
import Game.IllegalMoveException;
import Zeeslag.*;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {
        // Create ZeeslagBoards for Player 1 and Player 2
        ZeeslagBoard board1 = new ZeeslagBoard(10,10);
        ZeeslagBoard board2 = new ZeeslagBoard(10,10);

        // Create PlayerZeeslag instances for Player 1 and Player 2
        PlayerZeeslag player1 = new PlayerZeeslag("Player Joe");
        PlayerZeeslag player2 = new PlayerZeeslag("Player Dozo");

        // Set ZeeslagBoards for each player
        player1.setZeeslagBoard(board1);
        player2.setZeeslagBoard(board2);

        // Create Moves instance with the ZeeslagBoards for both players
        Moves moves = new Moves(board1, board2);


        //  Switch players and place a ship for each player

        // player 1 begint
        moves.switchPlayer();
        try {
            moves.placeShip(new Ship("Patrouilleschip"), 0, 0, true);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        moves.switchPlayer();
        try {
            moves.placeShip(new Ship("Onderzeeër"), 2, 3, false);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        moves.switchPlayer();
        try{moves.placeShip(new Ship("Slagschip"),3,5,true);}
        catch (IllegalMoveException e){
            e.printStackTrace();
        }

        moves.switchPlayer();
        try{moves.placeShip(new Ship("Vliegdekschip"),1,2,false);}
        catch (IllegalMoveException e){
            e.printStackTrace();
        }

        // test for IllegalMoveException
        moves.switchPlayer();
        try{moves.placeShip(new Ship("Slagschip"),0,0,true);}
        catch (IllegalMoveException e){
            e.printStackTrace();
        }


        // Print the status of the boards
        System.out.println("Player 1's Board:\n" + board1);
        System.out.println("Player 2's Board:\n" + board2);
    }
}
