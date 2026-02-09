package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.map.TileType;
import br.com.marco.pacman.util.Position;

import java.awt.Graphics;

public abstract class Entity {

    protected Position position;
    protected Direction direction;
    protected int speed;
    protected GameMap gameMap;
    protected boolean teleportLock = false;

    protected Entity(Position position, GameMap map) {
        this.position = position;
        this.gameMap = map;
        this.direction = Direction.NONE;
        this.speed = 0;
    }

    protected boolean collides(int x, int y) {
        int left = x / GameMap.TILE_SIZE;
        int right = (x + GameMap.TILE_SIZE - 1) / GameMap.TILE_SIZE;
        int top = y / GameMap.TILE_SIZE;
        int bottom = (y + GameMap.TILE_SIZE - 1) / GameMap.TILE_SIZE;

        return gameMap.isWall(top, left)
                || gameMap.isWall(top, right)
                || gameMap.isWall(bottom, left)
                || gameMap.isWall(bottom, right);
    }

    protected boolean canMove(Direction dir) {
        int nextX = position.x;
        int nextY = position.y;

        switch (dir) {
            case UP -> nextY -= speed;
            case DOWN -> nextY += speed;
            case LEFT -> nextX -= speed;
            case RIGHT -> nextX += speed;
        }

        return !collides(nextX, nextY);
    }

    protected void moveStraight() {
        int nextX = position.x;
        int nextY = position.y;

        switch (direction) {
            case UP -> nextY -= speed;
            case DOWN -> nextY += speed;
            case LEFT -> nextX -= speed;
            case RIGHT -> nextX += speed;
        }

        if (!collides(nextX, nextY)) {
            position.x = nextX;
            position.y = nextY;
        }

        checkTeleport();
    }

    protected void checkTeleport() {
        int col = (position.x + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;
        int row = (position.y + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;

        TileType tile = gameMap.getTile(row, col);

        if (tile == TileType.TELEPORT && !teleportLock) {
            Position target = gameMap.findOtherTeleport(row, col);
            if (target != null) {
                position.x = target.x;
                position.y = target.y;
                teleportLock = true;
            }
        }

        if (tile != TileType.TELEPORT) {
            teleportLock = false;
        }
    }

    public abstract void render(Graphics g);
    public abstract void update();
}
