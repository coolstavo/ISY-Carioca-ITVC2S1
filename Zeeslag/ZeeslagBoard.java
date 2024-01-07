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

}
