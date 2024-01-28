package Game;

import Zeeslag.ZeeslagBoard;

public interface Moveable {

    void placeMove(int row, int column, ZeeslagBoard own, ZeeslagBoard opponent);

    boolean checkMove(int row, int column) throws IllegalMoveException;

}
