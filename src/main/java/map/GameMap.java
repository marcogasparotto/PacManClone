package br.com.marco.pacman.map;

import br.com.marco.pacman.entity.Ghost;
import br.com.marco.pacman.util.Position;
import br.com.marco.pacman.util.Constants;
import br.com.marco.pacman.entity.PacMan;
import br.com.marco.pacman.entity.PinkGhost;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameMap {

    public static final int TILE_SIZE = Constants.TILE_SIZE;
    private PacMan pacMan;
    private final List<Ghost> ghosts = new ArrayList<>();

    private Position pinkSpawn;
    private Position yellowSpawn;
    private Position lightBlueSpawn;
    private Position orangeSpawn;

    private final int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {4, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 4},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1},
            {4, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 4},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
            {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public void registerPacMan(PacMan p) {
        this.pacMan = p;
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    public void registerGhost(Ghost ghost) {
        ghosts.add(ghost);
    }

    public void triggerFrightenedMode() {
        for (Ghost ghost : ghosts) {
            ghost.setFrightened();
        }
    }

    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }

    public TileType getTile(int row, int col) {
        if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
            return TileType.WALL;
        }
        return TileType.fromCode(map[row][col]);
    }

    public boolean isWall(int row, int col) {
        return getTile(row, col) == TileType.WALL;
    }

    public void setTile(int row, int col, TileType type) {
        if (row < 0 || col < 0 || row >= getRows() || col >= getCols())
            return;
        map[row][col] = type.getCode();
    }

    public Position getPinkSpawn() {
        return (pinkSpawn != null) ? pinkSpawn.copy() : getDefaultGhostSpawn();
    }

    public Position getYellowSpawn() {
        return (yellowSpawn != null) ? yellowSpawn.copy() : getDefaultGhostSpawn();
    }

    public Position getLightBlueSpawn() {
        return (lightBlueSpawn != null) ? lightBlueSpawn.copy() : getDefaultGhostSpawn();
    }

    public Position getOrangeSpawn() {
        return (orangeSpawn != null) ? orangeSpawn.copy() : getDefaultGhostSpawn();
    }

    private Position getDefaultGhostSpawn() {
        int row = 12;
        int col = 14;
        return new Position(col * TILE_SIZE, row * TILE_SIZE);
    }

    public Position findOtherTeleport(int row, int col) {
        for (int c = 0; c < getCols(); c++) {
            if (c != col && map[row][c] == TileType.TELEPORT.getCode()) {
                return new Position(c * TILE_SIZE, row * TILE_SIZE);
            }
        }
        return null;
    }

    public List<Position> findPath(Position start, Position end) {
        int rows = getRows();
        int cols = getCols();

        boolean[][] visited = new boolean[rows][cols];
        Position[][] parent = new Position[rows][cols];
        Queue<Position> queue = new LinkedList<>();

        queue.add(start);
        visited[start.y][start.x] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (p.x == end.x && p.y == end.y) break;

            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= cols || ny >= rows) continue;
                if (visited[ny][nx] || isWall(ny, nx)) continue;

                visited[ny][nx] = true;
                parent[ny][nx] = p;
                queue.add(new Position(nx, ny));
            }
        }

        if (!visited[end.y][end.x]) return null;

        List<Position> path = new ArrayList<>();
        Position cur = end;
        while (!(cur.x == start.x && cur.y == start.y)) {
            path.add(0, cur);
            cur = parent[cur.y][cur.x];
        }
        return path;
    }

    public Ghost getPinkGhost() {
        for (Ghost g : ghosts) {
            if (g instanceof PinkGhost) return g;
        }
        return null;
    }

    public boolean allPelletsEaten() {
        int rows = getRows();
        int cols = getCols();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == TileType.PELLET.getCode() ||
                        map[r][c] == TileType.POWER_PELLET.getCode()) {
                    return false;
                }
            }
        }
        return true;
    }
}
