package org.kairev0;

public class Player {
    private String name;
    private int score;
    private int countOfWins;
    private int countOfFails;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        this.countOfWins = 0;
        this.countOfFails = 0;
    }
}
