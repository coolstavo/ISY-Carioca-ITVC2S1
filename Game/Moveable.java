package Game;

public interface Moveable {

    public void placeMove(int row, int column, String piece);

    public boolean checkMove(int row, int column) throws IllegalMoveException;

    public boolean checkMiss(int row, int column) throws IllegalMoveException;

}
