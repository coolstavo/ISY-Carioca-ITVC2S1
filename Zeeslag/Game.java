package Zeeslag;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import Game.IllegalMoveException;

public class Game {

    // Create boards and moves for player 1
    ZeeslagPlayer player1;
    ZeeslagBoard ShipPlacementBoardP1;
    ZeeslagBoard PlayBoardP1;
    Moves PlaceMovesP1;
    Moves PlayMovesP1;

    // Create boards and moves for player 2
    ZeeslagPlayer player2;
    ZeeslagBoard ShipPlacementBoardP2;
    ZeeslagBoard PlayBoardP2;
    Moves PlaceMovesP2;
    Moves PlayMovesP2;

    // Create ships
    Ship P;
    Ship O;
    Ship S;
    Ship V;

    // Create list of ships to place
    List<Ship> shipsToPlace;


    // Create boolean to check if the game is finished and if the ships are placed
    boolean isFinished = false;
    boolean shipsPlaced = false;

    public Game() throws ShipNotAvailableException {

        // Create board + moves for P1
        this.ShipPlacementBoardP1 = new ZeeslagBoard(8, 8);
        this.PlayBoardP1 = new ZeeslagBoard(8, 8);

        this.PlaceMovesP1 = new Moves(ShipPlacementBoardP1);
        this.PlayMovesP1 = new Moves(PlayBoardP1);

        //------------------------------------------------

        // Create board + moves for P2
        this.ShipPlacementBoardP2 = new ZeeslagBoard(8, 8);
        this.PlayBoardP2 = new ZeeslagBoard(8, 8);

        this.PlaceMovesP2 = new Moves(ShipPlacementBoardP2);
        this.PlayMovesP2 = new Moves(PlayBoardP2);

        //------------------------------------------------

        // Create the ships
        this.P = new Ship(Ship.PATROUILLESCHIP);
        this.O = new Ship(Ship.ONDERZEEER);
        this.S = new Ship(Ship.SLAGSCHIP);
        this.V = new Ship(Ship.VLIEGDEKSCHIP);

        shipsToPlace = Arrays.asList(P, O, S, V);
    }

    public void start() throws ShipNotAvailableException, IllegalMoveException {

        while (!isFinished) {

            // Create players
            System.out.println("Welcome to Battleship!");
            System.out.println("Player 1, what is your name?: ");
            Scanner scanner = new Scanner(System.in);
            String nameP1 = scanner.nextLine();
            player1 = new ZeeslagPlayer(nameP1, PlayMovesP1, PlaceMovesP1, ShipPlacementBoardP1, PlayBoardP1);

            System.out.println("Player 2, what is your name?: ");
            String nameP2 = scanner.nextLine();
            player2 = new ZeeslagPlayer(nameP2, PlayMovesP2, PlaceMovesP2, ShipPlacementBoardP2, PlayBoardP2);

            System.out.println("Welcome " + player1.getName() + " and " + player2.getName());
            System.out.println("------------------------------------------------");

            //Ship placement
            if (!shipsPlaced) {
                System.out.println(player1.getName() + " place your ships!");
                placeShips(ShipPlacementBoardP1, PlaceMovesP1);

                System.out.println("------------------------------------------------");

                System.out.println(player2.getName() + " place your ships!");
                placeShips(ShipPlacementBoardP2, PlaceMovesP2);
                shipsPlaced = true;

                System.out.println("------------------------------------------------");

                System.out.println(player1.getName() + "'s board: ");
                System.out.println(ShipPlacementBoardP1);

                System.out.println(player2.getName() + "'s board: ");
                System.out.println(ShipPlacementBoardP2);

                System.out.println("------------------------------------------------");
            }

            System.out.println("Lets play!");


            //Play
            System.out.println(player1.getName() + " make your move!");
            player1.makeMove();
            checkWinner(player1, PlayBoardP1);

            System.out.println(player2.getName() + " make your move!");
            player2.makeMove();
            checkWinner(player2, PlayBoardP2);

            isFinished = true;
        }
    }

    public void checkWinner(ZeeslagPlayer player, ZeeslagBoard playBoard) {
        if (playBoard.getDestroyedShips().size() == 4) {
            declareWinner(player);
            isFinished = true;
        }
    }

    public void declareWinner(ZeeslagPlayer player) {
        System.out.println("Gefeliciteerd, Player " + player.getName() + " wins!");
    }

    public void placeShips(ZeeslagBoard board, Moves moves) throws ShipNotAvailableException, IllegalMoveException {
        for (Ship ship : shipsToPlace) {
            boolean shipPlaced = false;

            while (!shipPlaced) {

                Scanner scanner = new Scanner(System.in);

                System.out.println("Place the ship: " + ship.getType() + " (" + ship.getLength() + " cells)");
                System.out.println();
                System.out.println(board);
                System.out.println();

                int direction;
                do {
                    System.out.println("1 Horizontally: ");
                    System.out.println("2 Vertically: ");
                    direction = scanner.nextInt();
                    if (direction != 1 && direction != 2) {
                        System.out.println("Invalid direction, choose 1 for horizontal or 2 for vertical.");
                    }
                } while (direction != 1 && direction != 2);

                // Validate row input
                int row;
                do {
                    System.out.println("Choose a row (0 to " + (board.getNrOfRows() - 1) + "): ");
                    row = scanner.nextInt();
                    if (row < 0 || row >= board.getNrOfRows()) {
                        System.out.println("Invalid row. Enter a row within the board size.");
                    }
                } while (row < 0 || row >= board.getNrOfRows());

                // Validate column input
                int column;
                do {
                    System.out.println("Choose a column (0 to " + (board.getNrOfColumns() - 1) + "): ");
                    column = scanner.nextInt();
                    if (column < 0 || column >= board.getNrOfColumns()) {
                        System.out.println("Invalid column. Enter a column within the board size.");
                    }
                } while (column < 0 || column >= board.getNrOfColumns());

                // Place ship
                moves.placeShip(ship, row, column, direction == 1);

                // Ship is placed
                shipPlaced = true;
                System.out.println(board);
                System.out.println("-----------------------------------------");

            }
        }
    }
}