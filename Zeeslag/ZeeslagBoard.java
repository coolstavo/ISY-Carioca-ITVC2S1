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


    public boolean isShipAlreadyPlaced(int row, int column) {
        for (Ship ship : placedShips) {
            if (row == ship.getStartRow() && column == ship.getStartColumn()) {
                return true;
            }
        }
        return false;
    }


    public Ship getShipAt(int row, int column) {
        for (Ship ship : placedShips) {
            int startRow = ship.getStartRow();
            int startColumn = ship.getStartColumn();

            if (ship.isHorizontal() && row == startRow && column >= startColumn && column < startColumn + ship.getLength()) {
                return ship;
            } else if (!ship.isHorizontal() && column == startColumn && row >= startRow && row < startRow + ship.getLength()) {
                return ship;
            }
        }
        return null;
    }





}