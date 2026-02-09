package br.com.marco.pacman.ui;

import br.com.marco.pacman.entity.*;
import br.com.marco.pacman.game.ScoreManager;
import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.map.TileFactory;
import br.com.marco.pacman.map.TileType;
import br.com.marco.pacman.util.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private GameState currentState = GameState.MENU;

    private GameMap gameMap;
    private ScoreManager scoreManager;

    private final MenuPanel menuPanel;
    private final GameOverPanel gameOverPanel;
    private final WinPanel winPanel;

    private PacMan pacMan;
    private final List<Ghost> ghosts = new ArrayList<>();

    public GamePanel() {

        this.gameMap = new GameMap();
        this.scoreManager = new ScoreManager();

        this.menuPanel = new MenuPanel(this);
        this.gameOverPanel = new GameOverPanel(this);
        this.winPanel = new WinPanel();

        pacMan = new PacMan(
                new Position(14 * GameMap.TILE_SIZE, 20 * GameMap.TILE_SIZE),
                gameMap,
                scoreManager
        );

        spawnGhosts();

        setPreferredSize(new Dimension(
                gameMap.getCols() * GameMap.TILE_SIZE,
                gameMap.getRows() * GameMap.TILE_SIZE
        ));

        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (currentState == GameState.MENU) {
                    handleMenuInput(e);
                }
                else if (currentState == GameState.GAME_OVER) {
                    handleGameOverInput(e);
                }
                else if (currentState == GameState.WIN) {
                    handleWinInput(e);
                }
                else {
                    handleGameInput(e);
                }
            }
        });
    }

    private void spawnGhosts() {

        ghosts.clear();

        Ghost g1 = new PinkGhost(
                new Position(12 * GameMap.TILE_SIZE, 13 * GameMap.TILE_SIZE),
                gameMap
        );

        Ghost g2 = new YellowGhost(
                new Position(16 * GameMap.TILE_SIZE, 13 * GameMap.TILE_SIZE),
                gameMap
        );

        Ghost g3 = new LightBlueGhost(
                new Position(12 * GameMap.TILE_SIZE, 11 * GameMap.TILE_SIZE),
                gameMap
        );

        Ghost g4 = new OrangeGhost(
                new Position(16 * GameMap.TILE_SIZE, 11 * GameMap.TILE_SIZE),
                gameMap
        );

        ghosts.add(g1);
        ghosts.add(g2);
        ghosts.add(g3);
        ghosts.add(g4);

        gameMap.registerGhost(g1);
        gameMap.registerGhost(g2);
        gameMap.registerGhost(g3);
        gameMap.registerGhost(g4);
    }

    private void handleMenuInput(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP -> menuPanel.moveUp();
            case KeyEvent.VK_DOWN -> menuPanel.moveDown();

            case KeyEvent.VK_ENTER -> {
                if (menuPanel.getSelectedOption() == 0)
                    currentState = GameState.PLAYING;
                else
                    System.exit(0);
            }
        }

        repaint();
    }

    private void handleGameOverInput(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP -> gameOverPanel.moveUp();
            case KeyEvent.VK_DOWN -> gameOverPanel.moveDown();

            case KeyEvent.VK_ENTER -> {

                if (gameOverPanel.getSelectedOption() == 0) {
                    restartGame();
                    currentState = GameState.PLAYING;
                } else {
                    System.exit(0);
                }
            }
        }

        repaint();
    }

    private void handleWinInput(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            currentState = GameState.MENU;
        }

        repaint();
    }

    private void handleGameInput(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP -> pacMan.setDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> pacMan.setDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> pacMan.setDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> pacMan.setDirection(Direction.RIGHT);
        }
    }

    public void updateGame() {

        if (currentState != GameState.PLAYING) {
            repaint();
            return;
        }

        pacMan.update();

        if (!hasAnyPelletLeft()) {
            currentState = GameState.WIN;
            repaint();
            return;
        }

        for (Ghost ghost : ghosts) {

            ghost.update();

            if (ghost.collidesWith(pacMan)) {

                if (pacMan.isPowerMode() && ghost.isFrightened()) {
                    ghost.eaten();
                    scoreManager.addPowerPellet();
                }
                else if (!pacMan.isPowerMode() && !ghost.isEaten()) {

                    pacMan.die();

                    if (!pacMan.isAlive()) {
                        currentState = GameState.GAME_OVER;
                    } else {
                        resetAfterDeath();
                    }

                    break;
                }
            }
        }

        repaint();
    }

    private boolean hasAnyPelletLeft() {

        for (int row = 0; row < gameMap.getRows(); row++) {
            for (int col = 0; col < gameMap.getCols(); col++) {

                TileType tile = gameMap.getTile(row, col);

                if (tile == TileType.PELLET || tile == TileType.POWER_PELLET) {
                    return true;
                }
            }
        }

        return false;
    }

    private void resetAfterDeath() {

        pacMan.resetToSpawn();

        for (Ghost g : ghosts) {
            g.resetToSpawn();
        }
    }

    private void restartGame() {

        gameMap = new GameMap();
        scoreManager = new ScoreManager();

        pacMan = new PacMan(
                new Position(14 * GameMap.TILE_SIZE, 20 * GameMap.TILE_SIZE),
                gameMap,
                scoreManager
        );

        spawnGhosts();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (currentState == GameState.MENU) {
            menuPanel.render(g);
        }
        else if (currentState == GameState.GAME_OVER) {
            gameOverPanel.render(g);
        }
        else if (currentState == GameState.WIN) {
            winPanel.render(g, getWidth(), getHeight());
        }
        else {
            drawGame(g);
        }
    }

    private void drawGame(Graphics g) {

        for (int row = 0; row < gameMap.getRows(); row++) {
            for (int col = 0; col < gameMap.getCols(); col++) {

                TileType tile = gameMap.getTile(row, col);
                BufferedImage sprite = TileFactory.getSprite(tile);

                if (sprite != null) {
                    g.drawImage(
                            sprite,
                            col * GameMap.TILE_SIZE,
                            row * GameMap.TILE_SIZE,
                            GameMap.TILE_SIZE,
                            GameMap.TILE_SIZE,
                            null
                    );
                }
            }
        }

        pacMan.render(g);

        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        String scoreText = "SCORE: " + scoreManager.getScore();
        g.drawString(scoreText, 16, 15);

        String livesText = "LIVES: " + pacMan.getLives();
        FontMetrics fm = g.getFontMetrics();
        int livesX = getWidth() - fm.stringWidth(livesText) - 16;

        g.drawString(livesText, livesX, 15);
    }
}