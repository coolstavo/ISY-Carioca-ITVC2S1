package Game;

import Server.Challengeable;

public abstract class Player {

    protected String name;
    protected int score;
    protected boolean isTurn;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.isTurn = false;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        score += points;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public abstract void makeMove(int row, int column);

    public abstract boolean checkMove(int row, int column);


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", isTurn=" + isTurn +
                '}';
    }
}

