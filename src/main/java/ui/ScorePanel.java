package br.com.marco.pacman.ui;

import java.awt.Graphics;

public class ScorePanel {

    private GamePanel gp;

    public ScorePanel(GamePanel gp) { this.gp = gp; }

    public void render(Graphics g) {
        g.drawString("SCOREBOARD", 50, 50);
    }
}
