package org.kairev0;

public class Game {
    private final Id id;
    private final int[][] field;
    private Player firstPlayer;
    private Player secondPlayer;

    public Game(Id id, Player firstPlayer, Player secondPlayer) {
        this.id = id;
        this.field = new int[3][3];
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    private void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    private void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    private Id getId() {
        return id;
    }

    public int[][] getField() {
        return field;
    }

    private Player getFirstPlayer() {
        return firstPlayer;
    }

    private Player getSecondPlayer() {
        return secondPlayer;
    }
}
