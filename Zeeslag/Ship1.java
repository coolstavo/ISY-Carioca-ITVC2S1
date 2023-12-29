package Zeeslag;

import java.util.ArrayList;
import java.util.List;
import Game.Board;

public class Ship1 {

    private String[] typeDefiner = {"Patrouilleschip", "Onderzeeër", "Slagschip", "Vliegdekschip"};
    private int length;

    private int[] startCoordinate;
    private int[] endCoordinate;

    private List<String> availableShips = new ArrayList<>(List.of(typeDefiner));
    private String type;

    private Board board;

    //---------------------------CONSTRUCTOR------------------------------------
    /**
     * Constructor for the ship class
     * @param startCoordinate
     * @param endCoordinate
     */
    public Ship1(int[] startCoordinate, int[] endCoordinate) throws ShipNotAvailableException {
        this.length = calculateLength(startCoordinate, endCoordinate);

        if (!shipAvailability(length)) {
            throw new ShipNotAvailableException("Ship type not available");
        }
    }

    //---------------------------GETTERS------------------------------------

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }


    //----------------------------METHODS-----------------------------------

    private int calculateLength(int[] startCoordinate, int[] endCoordinate) {
        return Math.abs((startCoordinate[0] - endCoordinate[0]) + (startCoordinate[1] - endCoordinate[1]) + 1);
    }

    public boolean shipAvailability(int shipLen) {
        /**
         * returns true if ship is available, false if ship is not available
         * @param shipLen
         */
        switch (shipLen) {
            case 2:
                if (availableShips.contains(typeDefiner[0])) {
                    this.type = typeDefiner[0];
                    availableShips.remove(type);
                    return true;
                } else return false;
            case 3:
                if (availableShips.contains(typeDefiner[1])) {
                    this.type = typeDefiner[1];
                    availableShips.remove(type);
                    return true;
                } else return false;
            case 4:
                if (availableShips.contains(typeDefiner[2])) {
                    this.type = typeDefiner[2];
                    availableShips.remove(type);
                    return true;
                } else return false;
            case 6:
                if (availableShips.contains(typeDefiner[3])) {
                    this.type = typeDefiner[3];
                    availableShips.remove(type);
                    return true;
                } else return false;
            default:
                return false;
        }
    }

}
