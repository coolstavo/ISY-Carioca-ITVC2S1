package Zeeslag;

import java.util.ArrayList;
import java.util.List;
import Game.Board;

public class Ship {

    private String[] typeDefiner= new String[]{"Patrouileschip", "Onderzeeër", "Slagschip", "Vliegdekschip"};
    private int length;

    private int[] startCoordinate;
    private int[] endCoordnate;

    private List<String> availableShips = new ArrayList<>(List.of(typeDefiner));
    private String orientation;
    private String type;

    private Board Board;



    public Ship(int[] startCoordinate, int[] endCoordnate) throws ShipNotAvailableExeption {
        /**
         * Constructor for the ship class
         * @param startCoordinate
         * @param endCoordnate
         */
        this.length = Math.abs((startCoordinate[0]-endCoordnate[0])+(startCoordinate[1]-endCoordnate[1])+1);

        if (shipAvailability(this.length)){
            Board.checkMoveLegality(startCoordinate,endCoordnate);
        }

    }

    //----------------------------METHODS-----------------------------------

    public boolean shipAvailability(int shiplen){
        /**
         * returns true if ship is available, false if ship is not available
         * @param shiplen
         */
        switch (shiplen) {
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
