package Game;

public interface Moveable {

    public void PlaceMove(int row, int column, String piece);

    public boolean CheckMove(int row, int column) throws IllegalMoveException;

}
