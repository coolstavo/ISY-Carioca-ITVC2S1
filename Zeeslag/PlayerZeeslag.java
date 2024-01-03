package Zeeslag;

import Game.Player;

public class PlayerZeeslag extends Player {

    private ZeeslagBoard zeeslagBoard;

    public PlayerZeeslag(String name) {
        super(name);
        this.zeeslagBoard = new ZeeslagBoard(10, 10);
    }

    public ZeeslagBoard getZeeslagBoard() {
        return zeeslagBoard;
    }
    public void setZeeslagBoard(ZeeslagBoard zeeslagBoard) {
        this.zeeslagBoard = zeeslagBoard;
    }

    @Override
    public void makeMove(int row, int column) {
        // Implement if needed for gameplay
    }

    @Override
    public boolean checkMove(int row, int column) {
        // Implement if needed for gameplay
        return false;
    }
}
