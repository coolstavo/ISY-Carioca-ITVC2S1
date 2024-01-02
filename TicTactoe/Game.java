package TicTactoe;

public class Game {

    protected String name;
    protected String description;

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
