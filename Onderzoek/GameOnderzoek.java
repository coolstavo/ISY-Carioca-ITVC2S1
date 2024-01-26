package Onderzoek;

//--------------IMPORTS---------------------
import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;
import Zeeslag.ZeeslagBoard;

import java.util.Arrays;
import java.util.List;

public class GameOnderzoek {

    //--------------ATTRIBUTES---------------------

    // Create boards and moves for player 1
    private ZeeslagAI AI;
    private ZeeslagBoard ShipPlacementBoardAI;
    private ZeeslagBoard PlayBoardAI;
    private Moves PlaceMovesAI;
    private Moves PlayMovesAI;

    // Create boards and moves for player 2
    private ZeeslagAI RandomAI;
    private ZeeslagBoard ShipPlacementBoardRandomAI;
    private ZeeslagBoard PlayBoardRandomAI;
    private  Moves PlaceMovesRandomAI;
    private  Moves PlayMovesRandomAI;

    // Create ships
    private Ship P;
    private Ship M;
    private  Ship S;
    private  Ship V;

    // Create list of ships to place
    private List<Ship> shipsToPlace;

    // Create boolean to check if the game is finished and if the ships are placed
    private boolean isFinished = false;
    private  boolean shipsPlaced = false;
    private  boolean playingGame = false;

    //------------------------------------CONSTRUCTOR-----------------------------------------

    public GameOnderzoek() throws ShipNotAvailableException {

        // Create board + moves for P1
        this.ShipPlacementBoardAI = new ZeeslagBoard(8, 8);
        this.PlayBoardAI = new ZeeslagBoard(8, 8);

        this.PlaceMovesAI = new Moves(ShipPlacementBoardAI);
        this.PlayMovesAI = new Moves(PlayBoardAI);

        //------------------------------------------------

        // Create board + moves for P2
        this.ShipPlacementBoardRandomAI = new ZeeslagBoard(8, 8);
        this.PlayBoardRandomAI = new ZeeslagBoard(8, 8);

        this.PlaceMovesRandomAI = new Moves(ShipPlacementBoardRandomAI);
        this.PlayMovesRandomAI = new Moves(PlayBoardRandomAI);

        //------------------------------------------------

        // Create the ships
        this.P = new Ship(Ship.PATROUILLESCHIP);
        this.M = new Ship(Ship.MIJNENJAGER);
        this.S = new Ship(Ship.SLAGSCHIP);
        this.V = new Ship(Ship.VLIEGDEKSCHIP);

        shipsToPlace = Arrays.asList(P, M, S, V);
    }

    //------------------------------------START-----------------------------------------

    public void start() throws ShipNotAvailableException, IllegalMoveException {

        while (!isFinished) {

            //AI's aanmaken
            AI = new ZeeslagAI("AI", PlayMovesAI, PlaceMovesAI, ShipPlacementBoardAI, PlayBoardAI);
            RandomAI = new ZeeslagAI("RandomAI", PlayMovesRandomAI, PlaceMovesRandomAI, ShipPlacementBoardRandomAI, PlayBoardRandomAI);

            //Ship placement
            //AI
            PlaceMovesAI.placeShip(P, 1, 1, false);
            PlaceMovesAI.placeShip(M, 1, 4, true);
            PlaceMovesAI.placeShip(S, 4, 7, false);
            PlaceMovesAI.placeShip(V, 5, 0, true);

            //RandomAI
            PlaceMovesRandomAI.placeShip(P, 1, 1, false);
            PlaceMovesRandomAI.placeShip(M, 1, 4, true);
            PlaceMovesRandomAI.placeShip(S, 4, 7, false);
            PlaceMovesRandomAI.placeShip(V, 5, 0, true);


            while (!isFinished) {

                System.out.println("AI's turn");
                AI.makeMove(RandomAI);
                System.out.println(ShipPlacementBoardAI);
                System.out.println(PlayBoardAI);
                checkWinner(AI, ShipPlacementBoardRandomAI);


                System.out.println("-----------------------------------");
                System.out.println("-----------------------------------");

                if (playingGame) {
                    // The game is finished, no need for further player turns
                    isFinished = true;
                    break;
                }

                System.out.println("RandomAI's turn");
                RandomAI.makeRandomMove(AI);
                System.out.println(ShipPlacementBoardRandomAI);
                System.out.println(PlayBoardRandomAI);
                checkWinner(RandomAI, ShipPlacementBoardAI);

                System.out.println("-----------------------------------");
                System.out.println("-----------------------------------");


                if (playingGame) {
                    // The game is finished, no need for further player turns
                    isFinished = true;
                    break;
                }
            }
        }
    }


    //------------------------------------ONDERZOEK-----------------------------------------

    public void onderzoekLoop() throws ShipNotAvailableException, IllegalMoveException {

        int aantalSpellen = 0;

        while(aantalSpellen < 100){
            aantalSpellen++;
            System.out.println("Spel " + aantalSpellen);
            start();
        }
    }

    //------------------------------------WIN-----------------------------------------

    public void checkWinner(ZeeslagAI player, ZeeslagBoard playBoard) {
        if (playBoard.getDestroyedShips().size() == 4) {
            declareWinner(player);
            playingGame = true;
        }
    }

    public void declareWinner(ZeeslagAI player) {
        System.out.println("Gefeliciteerd, Player " + player.getName() + " wins!");
    }

}