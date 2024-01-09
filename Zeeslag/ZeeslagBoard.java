package Zeeslag;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class ZeeslagBoard extends Board {

    private List<Ship> placedShips;

    //--------------------------------CONSTRUCTOR--------------------------------

    public ZeeslagBoard(int nrOfRows, int nrOfColumns) {
        super(nrOfRows, nrOfColumns);
        this.placedShips = new ArrayList<>();
    }

    //-------------------------------GETTERS--------------------------------

    public List<Ship> getPlacedShips() {
        return placedShips;
    }

    //-------------------------------METHODS--------------------------------

    public boolean isShipAlreadyPlaced(Ship ship) {
        return placedShips.contains(ship);
    }

    public void placeShip(Ship ship) {
        placedShips.add(ship);
    }

    public boolean isShipSunk(Ship ship) {
        for (int row = 0; row < getNrOfRows(); row++) {
            for (int column = 0; column < getNrOfColumns(); column++) {
                if (getBoard().get(row).get(column).equals(ship.getRepresentation())) {
                    return false;  // The ship is not fully hit, so it's not sunk
                }
            }
        }
        return true;  // The ship is fully hit, so it's sunk
    }



}

