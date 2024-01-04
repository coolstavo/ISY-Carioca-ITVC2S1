package Zeeslag;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import Game.IllegalMoveException;

public class Game {

    // Create boards and moves for player 1
    ZeeslagBoard ShipPlacementBoardP1;
    ZeeslagBoard PlayBoardP1;
    Moves PlaceMovesP1;
    Moves PlayMovesP1;

    // Create boards and moves for player 2
    ZeeslagBoard ShipPlacementBoardP2;
    ZeeslagBoard PlayBoardP2;;
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

        // Create the boards for player 1 and player 2
        this.ShipPlacementBoardP1 = new ZeeslagBoard(8, 8);
        this.PlayBoardP1 = new ZeeslagBoard(8, 8);
        this.ShipPlacementBoardP2 = new ZeeslagBoard(8, 8);
        this.PlayBoardP2 = new ZeeslagBoard(8, 8);

        // Create the moves for player 1 and player 2
        this.PlaceMovesP1 = new Moves(ShipPlacementBoardP1);
        this.PlayMovesP1 = new Moves(PlayBoardP1);
        this.PlaceMovesP2 = new Moves(ShipPlacementBoardP2);
        this.PlayMovesP2 = new Moves(PlayBoardP2);

        // Create the ships
        this.P = new Ship(Ship.PATROUILLESCHIP);
        this.O = new Ship(Ship.ONDERZEEER);
        this.S = new Ship(Ship.SLAGSCHIP);
        this.V = new Ship(Ship.VLIEGDEKSCHIP);

        shipsToPlace = Arrays.asList(P, O, S, V);
    }

    public void start() throws ShipNotAvailableException, IllegalMoveException {

        while (!isFinished) {

            if (!shipsPlaced) {
                placeShips(ShipPlacementBoardP1, PlaceMovesP1);
                placeShips(ShipPlacementBoardP2, PlaceMovesP2);
                shipsPlaced = true;

                System.out.println("Player 1 board: ");
                System.out.println(ShipPlacementBoardP1);

                System.out.println("Player 2 board: ");
                System.out.println(ShipPlacementBoardP2);
            }

            System.out.println("Lets play!");

            isFinished = true;
        }
    }

    public void placeShips(ZeeslagBoard board, Moves moves) throws ShipNotAvailableException, IllegalMoveException {

        for (Ship ship : shipsToPlace) {
            boolean shipPlaced = false;

            while (!shipPlaced) {

                try {
                    Scanner scanner = new Scanner(System.in);

                    System.out.println("Plaats the ship: " + ship.getType() + " (" + ship.getLength() + " cells)");
                    System.out.println();
                    System.out.println(board);
                    System.out.println();

                    System.out.println("1 Horizontaal: ");
                    System.out.println("2 Verticaal: ");

                    int direction = scanner.nextInt();

                    if (direction == 1) {

                        //row
                        System.out.println("Kies een rij: ");
                        int row = scanner.nextInt();

                        //column
                        if (row >= 0 && row < 8) {
                            System.out.println("Kies een kolom: ");
                            int column = scanner.nextInt();

                            //place ship
                            if (column >= 0 && column < 8) {
                                moves.placeShip(ship, row, column, true);

                            } else {
                                System.out.println("Deze kolom bestaat niet");
                            }


                        } else {
                            System.out.println("Deze rij bestaat niet");
                        }

                    //Direction == vertical
                    } else if (direction == 2) {
                        System.out.println("Kies een rij: ");
                        int row = scanner.nextInt();
                        if (row >= 0 && row < 8) {
                            System.out.println("Kies een kolom: ");
                            int column = scanner.nextInt();
                            if (column >= 0 && column < 8) {
                                moves.placeShip(ship, row, column, false);
                            } else {
                                System.out.println("Deze kolom bestaat niet");
                            }
                        } else {
                            System.out.println("Deze rij bestaat niet");
                        }
                    } else {
                        System.out.println("Ongeldige richting, kies 1 voor horizontaal of 2 voor verticaal.");
                    }

                    //Ship is placed
                    shipPlaced = true;
                    System.out.println(board);
                    System.out.println("-----------------------------------------");

                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter valid integers for row, column, and direction.");
                }
            }
        }
    }

}