package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.util.Position;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public abstract class Ghost extends Entity {

    protected BufferedImage normalSprite;
    protected BufferedImage frightenedSprite;
    protected BufferedImage eyesSprite;

    protected GhostState state = GhostState.LOCKED;
    protected long stateTimer;

    protected final Position spawn;

    private List<Position> path;
    private int pathIndex;
    private Position lastTarget;

    private boolean waitingInHouse = false;

    protected Ghost(Position spawn, GameMap map, String spriteName) {

        super(spawn.copy(), map);

        this.spawn = spawn.copy();
        this.speed = 1;
        this.stateTimer = System.currentTimeMillis();

        try {

            normalSprite = ImageIO.read(getClass().getResourceAsStream(
                    "/assets/sprites/ghosts/" + spriteName + ".png"
            ));

            frightenedSprite = ImageIO.read(getClass().getResourceAsStream(
                    "/assets/sprites/ghosts/blue_ghost.png"
            ));

            eyesSprite = ImageIO.read(getClass().getResourceAsStream(
                    "/assets/sprites/ghosts/black_ghost.png"
            ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        map.registerGhost(this);
    }

    protected abstract Position getChaseTarget(PacMan pacMan);

    @Override
    public void update() {

        if (waitingInHouse) {

            if (System.currentTimeMillis() - stateTimer >= 4000) {
                waitingInHouse = false;
                state = GhostState.CHASE;
                speed = 2;
                direction = Direction.NONE;
                resetPath();
            }

            return;
        }

        switch (state) {

            case LOCKED -> {
                if (System.currentTimeMillis() - stateTimer >= 4000) {
                    state = GhostState.CHASE;
                    speed = 2;
                    direction = Direction.NONE;
                    stateTimer = System.currentTimeMillis();
                    resetPath();
                }
                return;
            }

            case FRIGHTENED -> {
                if (System.currentTimeMillis() - stateTimer >= 7000) {
                    state = GhostState.CHASE;
                    speed = 2;
                    direction = Direction.NONE;
                    stateTimer = System.currentTimeMillis();
                    resetPath();
                }
                updateFrightened();
                return;
            }

            case EATEN -> {
                updateReturnToSpawn();
                return;
            }

            case CHASE -> updateChase();
        }
    }

    private void updateChase() {

        PacMan pacMan = gameMap.getPacMan();
        if (pacMan == null) return;

        Position from = clamp(getTile(position));
        Position target = clamp(getChaseTarget(pacMan));

        followPath(from, target, false);
    }

    protected void updateFrightened() {

        Position me = clamp(getTile(position));
        Position pac = clamp(getTile(gameMap.getPacMan().getPosition()));

        int bestDist = -1;
        Position best = null;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {

            int nx = me.x + dx[i];
            int ny = me.y + dy[i];

            if (gameMap.isWall(ny, nx))
                continue;

            Position candidate = new Position(nx, ny);

            int dist =
                    Math.abs(candidate.x - pac.x) +
                            Math.abs(candidate.y - pac.y);

            if (dist > bestDist) {
                bestDist = dist;
                best = candidate;
            }
        }

        if (best != null)
            followPath(me, clamp(best), true);
        else
            moveStraight();
    }

    protected void updateReturnToSpawn() {

        Position from = clamp(getTile(position));
        Position target = clamp(getTile(spawn));

        followPath(from, target, true);

        Position now = getTile(position);

        if (now.x == target.x && now.y == target.y) {
            finishDead();
        }
    }

    private void followPath(Position from, Position target, boolean allowReverse) {

        boolean changed =
                lastTarget == null ||
                        lastTarget.x != target.x ||
                        lastTarget.y != target.y;

        if (path == null || changed || pathIndex >= path.size()) {

            path = gameMap.findPath(from, target);
            lastTarget = target;

            if (path == null || path.isEmpty()) {
                pathIndex = 0;
                moveStraight();
                return;
            }

            if (path.get(0).x == from.x && path.get(0).y == from.y)
                pathIndex = 1;
            else
                pathIndex = 0;
        }

        if (pathIndex >= path.size()) {
            moveStraight();
            return;
        }

        Position next = path.get(pathIndex);

        int tx = next.x * GameMap.TILE_SIZE;
        int ty = next.y * GameMap.TILE_SIZE;

        moveTowards(tx, ty, allowReverse);

        boolean snapX = Math.abs(position.x - tx) <= speed;
        boolean snapY = Math.abs(position.y - ty) <= speed;

        if (snapX && snapY) {
            position.x = tx;
            position.y = ty;
            pathIndex++;
        }
    }

    private void finishDead() {

        position.x = spawn.x;
        position.y = spawn.y;

        waitingInHouse = true;
        stateTimer = System.currentTimeMillis();

        state = GhostState.LOCKED;
        speed = 1;
        direction = Direction.NONE;

        resetPath();
    }

    protected void moveTowards(int tx, int ty, boolean allowReverse) {

        int tileSize = GameMap.TILE_SIZE;

        int centerX = (position.x / tileSize) * tileSize + tileSize / 2;
        int centerY = (position.y / tileSize) * tileSize + tileSize / 2;

        boolean alignedX = Math.abs(position.x + tileSize / 2 - centerX) <= speed;
        boolean alignedY = Math.abs(position.y + tileSize / 2 - centerY) <= speed;

        if (direction == Direction.NONE || (alignedX && alignedY)) {

            Direction bestDirection = null;
            double minDistance = Double.MAX_VALUE;

            for (Direction d : Direction.values()) {

                if (d == Direction.NONE) continue;
                if (!canMove(d)) continue;

                if (!allowReverse && direction != Direction.NONE &&
                        d == getOppositeDirection(direction))
                    continue;

                int nextX = position.x + d.dx * tileSize;
                int nextY = position.y + d.dy * tileSize;

                double dist =
                        Math.abs(tx - nextX) +
                                Math.abs(ty - nextY);

                if (dist < minDistance) {
                    minDistance = dist;
                    bestDirection = d;
                }
            }

            if (bestDirection == null) {
                for (Direction d : Direction.values()) {
                    if (d == Direction.NONE) continue;
                    if (canMove(d)) {
                        bestDirection = d;
                        break;
                    }
                }
            }

            if (bestDirection != null)
                direction = bestDirection;
        }

        moveStraight();
    }

    private Direction getOppositeDirection(Direction d) {
        if (d == Direction.UP) return Direction.DOWN;
        if (d == Direction.DOWN) return Direction.UP;
        if (d == Direction.LEFT) return Direction.RIGHT;
        if (d == Direction.RIGHT) return Direction.LEFT;
        return null;
    }

    protected Position getTile(Position p) {

        return new Position(
                (p.x + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE,
                (p.y + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE
        );
    }

    private Position clamp(Position p) {

        int x = Math.max(0, Math.min(gameMap.getCols() - 1, p.x));
        int y = Math.max(0, Math.min(gameMap.getRows() - 1, p.y));

        return new Position(x, y);
    }

    private void resetPath() {
        path = null;
        pathIndex = 0;
        lastTarget = null;
    }

    public void eaten() {

        state = GhostState.EATEN;
        stateTimer = System.currentTimeMillis();
        speed = 3;
        direction = Direction.NONE;

        resetPath();
    }

    public void setFrightened() {

        if (state == GhostState.EATEN || state == GhostState.LOCKED)
            return;

        state = GhostState.FRIGHTENED;
        stateTimer = System.currentTimeMillis();
        speed = 1;
        direction = Direction.NONE;

        resetPath();
    }

    public boolean isFrightened() {
        return state == GhostState.FRIGHTENED;
    }

    public boolean isEaten() {
        return state == GhostState.EATEN;
    }

    public void resetToSpawn() {

        position.x = spawn.x;
        position.y = spawn.y;

        waitingInHouse = true;
        stateTimer = System.currentTimeMillis();

        state = GhostState.LOCKED;
        speed = 1;
        direction = Direction.NONE;

        resetPath();
    }

    public boolean collidesWith(PacMan pacman) {

        int size = GameMap.TILE_SIZE;

        return position.x < pacman.position.x + size &&
                position.x + size > pacman.position.x &&
                position.y < pacman.position.y + size &&
                position.y + size > pacman.position.y;
    }

    @Override
    public void render(Graphics g) {

        BufferedImage img = switch (state) {
            case FRIGHTENED -> frightenedSprite;
            case EATEN -> eyesSprite;
            default -> normalSprite;
        };

        g.drawImage(
                img,
                position.x,
                position.y,
                GameMap.TILE_SIZE,
                GameMap.TILE_SIZE,
                null
        );
    }
}