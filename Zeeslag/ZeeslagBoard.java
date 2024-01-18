package Zeeslag;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class ZeeslagBoard extends Board {

    private List<Ship> placedShips;
    private List<Ship> destroyedShips;

    //--------------------------------CONSTRUCTOR--------------------------------

    public ZeeslagBoard(int nrOfRows, int nrOfColumns) {
        super(nrOfRows, nrOfColumns);
        this.placedShips = new ArrayList<>();
        this.destroyedShips = new ArrayList<>();
    }

    //-------------------------------GETTERS--------------------------------

    public List<Ship> getPlacedShips() {
        return placedShips;
    }

    public List<Ship> getDestroyedShips() {
        return destroyedShips;
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
        destroyedShips.add(ship);
        return true;  // The ship is fully hit, so it's sunk
    }


    public boolean isShipDestroyed(Ship ship) {
        return destroyedShips.contains(ship);
    }

    public boolean allShipsDestroyed(ZeeslagPlayer player, ZeeslagBoard playBoard, List<Ship> shipsToPlace) {
        for (Ship ship : shipsToPlace) {
            if (!playBoard.isShipDestroyed(ship)) {
                return false;
            }
        }
        return true;
    }
}
