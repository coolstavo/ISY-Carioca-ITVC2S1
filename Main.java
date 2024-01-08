import Game.IllegalMoveException;
import Zeeslag.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {

        Game game = new Game();
        game.start();

    }
}