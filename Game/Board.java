package Game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<String> board = new ArrayList<String>();
    private int nrOfRows;
    private int nrOfColumns;

    public Board(int nrOfRows, int nrOfColumns) {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        createGrid(nrOfRows,nrOfColumns);
    }

    //---------------------------GETTERS------------------------------------

    public int getNrOfColumns() {
        return nrOfColumns;
    }

    public int getNrOfRows() {
        return nrOfRows;
    }

    //----------------------------METHODS-----------------------------------

    /**
     * Creates a 2d grid in an array with the coordinates in it
     */
    public void createGrid(int rows, int columns) {

        // Iterate through rows
        for (int i = 0; i < rows; i++) {

            // Iterate through columns
            for (int j = 0; j < columns; j++) {

                // Print the grid element (you can modify this to create your grid structure)
                String gridentry = "[" + i + "," + j + "]";
                this.board.add(gridentry);

            }
            this.board.add("\n");
        }
    }


    //-----------------------------OVERRIDES--------------------------------

    @Override
    public String toString() {
        return board.toString();
    }


}
