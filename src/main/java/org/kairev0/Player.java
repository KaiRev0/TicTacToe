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

    private void setName(String name) {
        this.name = name;
    }

    private void setScore(int score) {
        this.score = score;
    }

    private void setCountOfWins(int countOfWins) {
        this.countOfWins = countOfWins;
    }

    private void setCountOfFails(int countOfFails) {
        this.countOfFails = countOfFails;
    }

    public String getName() {
        return name;
    }

    private int getScore() {
        return score;
    }

    private String setName() {
        return name;
    }

    private int getCountOfWins() {
        return countOfWins;
    }

    private int getCountOfFails() {
        return countOfFails;
    }
}
