package Onderzoek;

//--------------IMPORTS---------------------
import Game.AI;
import Game.IllegalMoveException;
import Zeeslag.Moves;
import Zeeslag.ZeeslagBoard;

import java.util.Random;

public class ZeeslagAI extends AI {

    //--------------ATTRIBUTES---------------------

    private Moves playMoves;
    private Moves placeMoves;

    private ZeeslagBoard shipPlacementBoard;
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
            int row = random.nextInt(shipPlacementBoard.getNrOfRows()- 1);
            int column = random.nextInt(shipPlacementBoard.getNrOfColumns()- 1);

            // Check the opponent's placement board for a hit or miss
            if (opponent.placeMoves.checkMiss(row, column)) {
                System.out.println("It's a MISS!");
                opponent.placeMoves.placeMove(row, column, Moves.MISS);

                // Update your play board with the result
                playMoves.placeMove(row, column, Moves.MISS);

            } else {
                System.out.println("It's a HIT!");
                opponent.placeMoves.placeHit(row, column);

                // Update your play board with the result
                playMoves.placeMove(row, column, Moves.HIT);
            }

            validMove = true; // Mark the move as valid to exit the loop
        }
    }

    //------------------------------------------------------AI---------------------------------------------------------

    public void makeMove(ZeeslagAI opponent) throws IllegalMoveException {
        boolean validMove = false;

        while (!validMove) {
            int row = lastHitRow;
            int column = lastHitColumn;

            // If there was a previous hit, continue in the current direction
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
                // If there was no previous hit, generate random row and column values
                Random random = new Random();
                row = random.nextInt(shipPlacementBoard.getNrOfRows() - 1);
                column = random.nextInt(shipPlacementBoard.getNrOfColumns() - 1);
            }

            // Ensure row and column are within the valid range
            row = Math.max(0, Math.min(row, shipPlacementBoard.getNrOfRows() - 1));
            column = Math.max(0, Math.min(column, shipPlacementBoard.getNrOfColumns() - 1));

            // Check the opponent's placement board for a hit or miss
            if (opponent.placeMoves.checkMiss(row, column)) {
                System.out.println("It's a MISS!");
                opponent.placeMoves.placeMove(row, column, Moves.MISS);
                playMoves.placeMove(row, column, Moves.MISS);

                // If there was a previous hit, switch direction
                if (lastHitRow != -1 && lastHitColumn != -1) {
                    direction = (direction + 1) % 4;
                }
            } else {
                System.out.println("It's a HIT!");
                opponent.placeMoves.placeHit(row, column);
                playMoves.placeMove(row, column, Moves.HIT);

                // Update the last hit position
                lastHitRow = row;
                lastHitColumn = column;
            }

            validMove = true; // Mark the move as valid to exit the loop
        }
    }
}