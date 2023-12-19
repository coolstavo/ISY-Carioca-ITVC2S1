package Game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<List<String>> board;
    private int nrOfRows;
    private int nrOfColumns;

    public Board(int nrOfRows, int nrOfColumns) {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.board = new ArrayList<>();
        createGrid(nrOfRows, nrOfColumns);
    }

    //---------------------------GETTERS------------------------------------

    public int getNrOfColumns() {
        return nrOfColumns;
    }

    public int getNrOfRows() {
        return nrOfRows;
    }

    public List<List<String>> getBoard() {
        return this.board;
    }

    //----------------------------BOARD-----------------------------------

    /**
     * Creates a grid with the specified number of rows and columns
     *
     * @param rows
     * @param columns
     * @return
     */
    public void createGrid(int rows, int columns) {

        // Iterate through rows
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();

            // Add an empty string to each cell in the current row
            for (int j = 0; j < columns; j++) {
                row.add(" ");
            }

            // Add the current row to the board
            this.board.add(row);
        }
    }

    //-----------------------------OVERRIDES--------------------------------

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Iterate through the board elements
        for (List<String> row : this.board) {
            // Print the values in the current row
            for (String value : row) {
                result.append("[").append(value).append("]");
            }
            result.append("\n");
        }

        return result.toString();
    }

    //-----------------------------METHODS----------------------------------

    /**
     * Checks if a contestant with the specified symbol has won the game.
     *
     * @param symbol the symbol to check for (e.g., 'X' or 'O')
     * @return true if the contestant has won, false otherwise
     */
    public boolean hasContestantWon(char symbol) {
        // Check rows
        for (List<String> row : this.board) {
            if (checkRow(row, symbol)) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < this.nrOfColumns; i++) {
            List<String> column = new ArrayList<>();
            for (List<String> row : this.board) {
                column.add(row.get(i));
            }
            if (checkRow(column, symbol)) {
                return true;
            }
        }

        // Check diagonals
        List<String> diagonal1 = new ArrayList<>();
        List<String> diagonal2 = new ArrayList<>();
        for (int i = 0; i < this.nrOfRows; i++) {
            diagonal1.add(this.board.get(i).get(i));
            diagonal2.add(this.board.get(i).get(this.nrOfColumns - 1 - i));
        }

        return checkRow(diagonal1, symbol) || checkRow(diagonal2, symbol);
    }

    /**
     * Helper method to check if a row is a winning combination.
     *
     * @param row    the row to check
     * @param symbol the symbol to check for
     * @return true if the row is a winning combination, false otherwise
     */
    private boolean checkRow(List<String> row, char symbol) {
        for (String cell : row) {
            if (!cell.equals(String.valueOf(symbol))) {
                return false; // If any cell does not match the symbol, return false
            }
        }
        return true; // All cells matched the symbol
    }


}
