import Game.IllegalMoveException;
import Onderzoek.GameOnderzoek;
import Zeeslag.*;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {

        GameOnderzoek game = new GameOnderzoek();
//        game.start();

        Game game2 = new Game();
        game2.start();

    }
}