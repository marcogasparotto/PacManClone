package br.com.marco.pacman;

import br.com.marco.pacman.game.Game;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Game().start();
        });

    }
}