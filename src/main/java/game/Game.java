package br.com.marco.pacman.game;

import br.com.marco.pacman.ui.GamePanel;

import javax.swing.*;

public class Game {

    private JFrame frame;
    private GamePanel gamePanel;
    private GameLoop gameLoop;

    public void start() {

        frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gameLoop = new GameLoop(gamePanel);
        gameLoop.start();
    }
}