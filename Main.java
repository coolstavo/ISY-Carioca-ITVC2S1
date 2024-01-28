import Game.IllegalMoveException;
import Onderzoek.CSVLogger;
import Onderzoek.GameOnderzoek;
import Zeeslag.*;

public class Main {

    public static void main(String[] args) throws ShipNotAvailableException, IllegalMoveException {

//        CSVLogger logger = new CSVLogger();
//        logger.writeLogFile();

        GameOnderzoek game = new GameOnderzoek();
        game.start();

    }
}