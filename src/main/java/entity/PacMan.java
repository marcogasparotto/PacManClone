package br.com.marco.pacman.entity;

import br.com.marco.pacman.game.ScoreManager;
import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.map.TileType;
import br.com.marco.pacman.util.Position;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PacMan extends Entity {

    private Direction nextDirection = Direction.NONE;

    private BufferedImage[][] sprites;
    private int currentFrame = 0;
    private int frameCounter = 0;
    private static final int FRAME_DELAY = 10;

    private final ScoreManager scoreManager;

    private int lives = 3;
    private final Position spawn;

    private boolean powerMode = false;
    private long powerStartTime;
    private static final long POWER_DURATION = 7000;

    private boolean dying = false;
    private long deathStart;
    private static final long DEATH_TIME = 1200;

    public PacMan(Position spawn, GameMap map, ScoreManager scoreManager) {
        super(spawn.copy(), map);
        this.spawn = spawn.copy();
        this.scoreManager = scoreManager;
        this.speed = 2;
        loadSprites();
        map.registerPacMan(this);
    }

    @Override
    public void update() {

        if (dying) {

            if (System.currentTimeMillis() - deathStart >= DEATH_TIME) {
                dying = false;
            }
            return;
        }

        tryTurn();
        moveStraight();
        eatPellet();
        updatePowerMode();
        animate();
    }

    private void tryTurn() {
        if (nextDirection == Direction.NONE || nextDirection == direction) return;

        if (canMove(nextDirection)) {
            direction = nextDirection;
        }
    }

    private void eatPellet() {

        int col = (position.x + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;
        int row = (position.y + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;

        TileType tile = gameMap.getTile(row, col);

        if (tile == TileType.PELLET) {
            gameMap.setTile(row, col, TileType.EMPTY);
            scoreManager.addPellet();
        }

        if (tile == TileType.POWER_PELLET) {
            gameMap.setTile(row, col, TileType.EMPTY);
            scoreManager.addPowerPellet();
            activatePowerMode();
            gameMap.triggerFrightenedMode();
        }
    }

    private void activatePowerMode() {
        powerMode = true;
        powerStartTime = System.currentTimeMillis();
    }

    private void updatePowerMode() {
        if (powerMode && System.currentTimeMillis() - powerStartTime > POWER_DURATION) {
            powerMode = false;
        }
    }

    public void die() {

        if (dying) return;

        lives--;
        dying = true;
        deathStart = System.currentTimeMillis();
        direction = Direction.NONE;
        nextDirection = Direction.NONE;
    }

    public void resetToSpawn() {

        position.x = spawn.x;
        position.y = spawn.y;

        direction = Direction.NONE;
        nextDirection = Direction.NONE;

        // IMPORTANTE
        powerMode = false;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public int getLives() {
        return lives;
    }

    public boolean isPowerMode() {
        return powerMode;
    }

    private void animate() {
        frameCounter++;
        if (frameCounter >= FRAME_DELAY) {
            frameCounter = 0;
            currentFrame = (currentFrame + 1) % 3;
        }
    }

    @Override
    public void render(Graphics g) {

        if (dying) {
            long t = System.currentTimeMillis() - deathStart;
            if ((t / 150) % 2 == 0) return;
        }

        int dirIndex = switch (direction) {
            case RIGHT -> 0;
            case LEFT -> 1;
            case UP -> 2;
            case DOWN -> 3;
            default -> 0;
        };

        g.drawImage(
                sprites[dirIndex][currentFrame],
                position.x,
                position.y,
                GameMap.TILE_SIZE,
                GameMap.TILE_SIZE,
                null
        );
    }

    public void setDirection(Direction dir) {
        this.nextDirection = dir;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    private void loadSprites() {

        String[] dirs = {"right", "left", "up", "down"};
        sprites = new BufferedImage[4][3];

        try {
            for (int d = 0; d < 4; d++) {
                for (int f = 0; f < 3; f++) {
                    sprites[d][f] = ImageIO.read(
                            getClass().getResourceAsStream(
                                    "/assets/sprites/pac_man/pac_man_" + dirs[d] + (f + 1) + ".png"
                            )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar sprites do Pac-Man", e);
        }
    }
}
