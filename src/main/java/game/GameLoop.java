package br.com.marco.pacman.game;

import br.com.marco.pacman.ui.GamePanel;

public class GameLoop implements Runnable {

    private final GamePanel gamePanel;
    private Thread thread;
    private boolean running;
    private static final int FPS = 60;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "GameLoop");
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        final long optimalTime = 1_000_000_000L / FPS;

        while (running) {
            long startTime = System.nanoTime();

            gamePanel.updateGame();

            gamePanel.repaint();

            long elapsed = System.nanoTime() - startTime;
            long sleepTime = (optimalTime - elapsed) / 1_000_000;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
