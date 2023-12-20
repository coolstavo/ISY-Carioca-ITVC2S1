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

        int rows = 0;
        int collumns = 0;

        result.append("  ");

        for (int i = 0; i < this.nrOfColumns; i++) {

            result.append(" ").append(collumns++).append(" ");
        }
        result.append('\n');

        // Iterate through the board elements
        for (List<String> row : this.board) {
            // Print the values in the current row

            result.append(rows++).append(" ");

            for (String value : row) {

                result.append("[").append(value).append("]");
            }
            result.append("\n");

        }

        return result.toString();
    }

}
