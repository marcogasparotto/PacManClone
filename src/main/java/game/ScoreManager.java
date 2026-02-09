package br.com.marco.pacman.game;

public class ScoreManager {

    private int score = 0;

    public void addPellet() {
        score += 10;
    }

    public void addPowerPellet() {
        score += 50;
    }

    public void addGhost() {
        score += 200;
    }

    public int getScore() {
        return score;
    }
}
