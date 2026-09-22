package org.kairev0;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;
    private int score;
    private int countOfWins;
    private int countOfFails;

    public Player(String name, String password, int score) {
        this.name = name;
        this.password = password;
        this.score = score;
        this.countOfWins = 0;
        this.countOfFails = 0;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setPassword(String password) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public int getScore() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Player player = (Player) o;
        return this.name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
