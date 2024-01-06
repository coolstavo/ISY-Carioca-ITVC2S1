package Zeeslag;

import java.util.Arrays;
import java.util.List;

/**
 * Ship class
 */
public class Ship {

    private static final List<String> SHIP_TYPES = Arrays.asList("Patrouilleschip", "Onderzeeër", "Slagschip", "Vliegdekschip");

    public static final String PATROUILLESCHIP = "Patrouilleschip";
    public static final String ONDERZEEER = "Onderzeeër";
    public static final String SLAGSCHIP = "Slagschip";
    public static final String VLIEGDEKSCHIP = "Vliegdekschip";

    private String type;
    private int length;
    private String representation;  // Representation of the ship (first letter of the type)
    private int startRow;  // Added property to store the start row
    private int startColumn;  // Added property to store the start column
    private boolean isHorizontal;  // Added property to store the orientation

    //---------------------------CONSTRUCTOR------------------------------------

    /**
     * Constructor for the ship class
     * @param type
     * @throws ShipNotAvailableException
     */
    public Ship(String type) throws ShipNotAvailableException {
        if (SHIP_TYPES.contains(type)) {
            this.type = type;
            this.length = calculateLength(type);
            this.representation = String.valueOf(type.charAt(0));  // Get the first letter of the type
        } else {
            throw new ShipNotAvailableException("Invalid ship type: " + type);
        }
    }

    //---------------------------GETTERS------------------------------------

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getRepresentation() {
        return representation;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    //---------------------------METHODS------------------------------------

    /**
     * Calculate the length of the ship based on the type
     * @param type
     * @return
     */
    private int calculateLength(String type) {
        // You can define the lengths for each ship type as needed
        switch (type) {
            case "Patrouilleschip":
                return 2;
            case "Onderzeeër":
                return 3;
            case "Slagschip":
                return 4;
            case "Vliegdekschip":
                return 6;
            default:
                return 0; // or throw an exception for unknown ship types
        }
    }

    //-------------------------------OVERRIDES--------------------------------

    @Override
    public String toString() {
        return this.type + " (" + this.representation + ")";
    }

    // Additional methods to set startRow, startColumn, and orientation
    public void setStartLocation(int startRow, int startColumn, boolean isHorizontal) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.isHorizontal = isHorizontal;
    }
}
