package Onderzoek;

//--------------IMPORTS---------------------
import Game.AI;
import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.ZeeslagBoard;

import java.util.Objects;
import java.util.Random;

public class ZeeslagAI extends AI {

    //--------------ATTRIBUTES---------------------

    private Moves playMoves;
    private Moves placeMoves;

    ZeeslagBoard shipPlacementBoard;
    private ZeeslagBoard playBoard;

    private int lastHitRow = -1;
    private int lastHitColumn = -1;
    private int direction = 0; // 0: right, 1: down, 2: left, 3: up

    //------------------------------------------------------CONSTRUCTOR---------------------------------------------------------

    public ZeeslagAI(String name, Moves playMoves, Moves placeMoves, ZeeslagBoard shipPlacementBoard, ZeeslagBoard playBoard) {
        super(name);
        this.playMoves = playMoves;
        this.placeMoves = placeMoves;

        this.shipPlacementBoard = shipPlacementBoard;
        this.playBoard = playBoard;
    }

    //------------------------------------------------RANDOM AI---------------------------------------------------------

    public void makeRandomMove(ZeeslagAI opponent) throws IllegalMoveException {
        boolean validMove = false;

        while (!validMove) {
            // Generate random row and column values
            Random random = new Random();
            int row = random.nextInt(shipPlacementBoard.getNrOfRows());
            int column = random.nextInt(shipPlacementBoard.getNrOfColumns());

            String currentCellState = opponent.shipPlacementBoard.getPiece(row, column);

            // If the cell is empty or contains a ship, make a move
            if (currentCellState.equals(" ") || currentCellState.equals("S") ||
                    currentCellState.equals("M") || currentCellState.equals("P") ||
                    currentCellState.equals("V")) {
                playMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
            }
            // If the cell already contains a hit or a miss, do not make a move
            else if (currentCellState.equals("X") || currentCellState.equals("O")) {
                // Do nothing
            } else {
                playMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
            }


            validMove = true; // Mark the move as valid to exit the loop
        }
    }

    //------------------------------------------------------AI---------------------------------------------------------

    public void makeMove(ZeeslagAI opponent) throws IllegalMoveException {
        boolean validMove = false;
        Random random = new Random();
        int attempts = 0;

        while (!validMove && attempts < 4) {
            int row = lastHitRow;
            int column = lastHitColumn;

            if (lastHitRow != -1 && lastHitColumn != -1) {
                switch (direction) {
                    case 0: // right
                        column++;
                        break;
                    case 1: // down
                        row++;
                        break;
                    case 2: // left
                        column--;
                        break;
                    case 3: // up
                        row--;
                        break;
                }
            } else {
                row = random.nextInt(shipPlacementBoard.getNrOfRows() - 1);
                column = random.nextInt(shipPlacementBoard.getNrOfColumns() - 1);
            }

            row = Math.max(0, Math.min(row, shipPlacementBoard.getNrOfRows() - 1));
            column = Math.max(0, Math.min(column, shipPlacementBoard.getNrOfColumns() - 1));

            String currentCellState = opponent.shipPlacementBoard.getPiece(row, column);

            System.out.println("Current cell state: " + currentCellState); // Debugging line
            System.out.println("Current direction: " + direction); // Debugging line

            if (currentCellState.equals(" ")) {
                playMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
                lastHitRow = row;
                lastHitColumn = column;
                validMove = true;
                System.out.println("Made a move to: (" + row + ", " + column + ")"); // Debugging line


            } else if (currentCellState.equals("X") || currentCellState.equals("O")) {
                lastHitRow = -1;
                lastHitColumn = -1;
                direction = (direction + 1) % 4; // Change direction
                attempts++;


            } else {
                playMoves.placeMove(row, column, playBoard, opponent.shipPlacementBoard);
                lastHitRow = row;
                lastHitColumn = column;
                validMove = true;
                System.out.println("Made a move to: (" + row + ", " + column + ")"); // Debugging line
            }
        }

        if (!validMove) {
            makeRandomMove(opponent);
        }
    }

}
