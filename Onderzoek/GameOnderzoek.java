package Onderzoek;

//--------------IMPORTS---------------------
import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.Ship;
import Zeeslag.ShipNotAvailableException;
import Zeeslag.ZeeslagBoard;

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
    public int roundCount = 0;

    // Create ships
    private Ship P;
    private Ship M;
    private  Ship S;
    private  Ship V;

    // Create boolean to check if the game is finished and if the ships are placed
    private boolean isFinished = false;

    CSVLogger logger = new CSVLogger();
    private String winner = "";

    //------------------------------------CONSTRUCTOR-----------------------------------------

    public GameOnderzoek() throws ShipNotAvailableException {

        // Create the ships
        this.P = new Ship(Ship.PATROUILLESCHIP);
        this.M = new Ship(Ship.MIJNENJAGER);
        this.S = new Ship(Ship.SLAGSCHIP);
        this.V = new Ship(Ship.VLIEGDEKSCHIP);

    }

    //------------------------------------START-----------------------------------------

    public void start() throws IllegalMoveException {

        for (int i = 1; i < 101; i++) {

            System.out.println("Spel " + i);

            // Reset the game state
            isFinished = false;

            // Create board + moves for P1
            ShipPlacementBoardAI = new ZeeslagBoard(8, 8);
            PlayBoardAI = new ZeeslagBoard(8, 8);

            PlaceMovesAI = new Moves(ShipPlacementBoardAI);
            PlayMovesAI = new Moves(PlayBoardAI);

            // Create board + moves for P2
            ShipPlacementBoardRandomAI = new ZeeslagBoard(8, 8);
            PlayBoardRandomAI = new ZeeslagBoard(8, 8);

            PlaceMovesRandomAI = new Moves(ShipPlacementBoardRandomAI);
            PlayMovesRandomAI = new Moves(PlayBoardRandomAI);

            AI = new ZeeslagAI("AI", PlayMovesAI, PlaceMovesAI, ShipPlacementBoardAI, PlayBoardAI);
            RandomAI = new ZeeslagAI("RandomAI", PlayMovesRandomAI, PlaceMovesRandomAI, ShipPlacementBoardRandomAI, PlayBoardRandomAI);

            PlaceMovesAI.placeShip(P, 3, 5, true);
            PlaceMovesAI.placeShip(M, 1, 3, true);
            PlaceMovesAI.placeShip(S, 3, 3, false);
            PlaceMovesAI.placeShip(V, 1, 1, false);

            //RandomAI
            PlaceMovesRandomAI.placeShip(P, 3, 5, true);
            PlaceMovesRandomAI.placeShip(M, 1, 3, true);
            PlaceMovesRandomAI.placeShip(S, 3, 3, false);
            PlaceMovesRandomAI.placeShip(V, 1, 1, false);

            roundCount = 0;

            while (!isFinished) {
                roundCount++;
                AI.makeMove(RandomAI);
                if (checkWinner(PlayBoardAI, AI) == 15) {
                    break;
                }

                RandomAI.makeRandomMove(AI);
                checkWinner(PlayBoardRandomAI, RandomAI);

            }
            logger.writeLogFile(i, roundCount, winner);
        }
    }

    //------------------------------------WIN------------------------------------------


    public int checkWinner(ZeeslagBoard board, ZeeslagAI player) {
        int count = 0;

        for (int row = 0; row < board.getNrOfRows(); row++) {
            for (int column = 0; column < board.getNrOfColumns(); column++) {
                if (board.getPiece(row, column).equals("X")) {
                    count++;
                }

                if (count == 15) {
                    System.out.println(declareWinner(player));
                    isFinished = true;
                }
            }
        }
        return count;
    }

    public String declareWinner(ZeeslagAI player) {
        winner = player.getName();
        return "Gefeliciteerd, Player " + player.getName() + " wins!" ;
    }
}