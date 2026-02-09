package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.util.Position;

public class PinkGhost extends Ghost {

    public PinkGhost(Position spawn, GameMap map) {
        super(spawn, map, "pink_ghost");
    }

    @Override
    protected Position getChaseTarget(PacMan pacMan) {

        Position pacTile = getTile(pacMan.getPosition());

        int x = pacTile.x;
        int y = pacTile.y;

        switch (pacMan.getDirection()) {
            case UP -> y -= 4;
            case DOWN -> y += 4;
            case LEFT -> x -= 4;
            case RIGHT -> x += 4;
        }

        return new Position(x, y);
    }
}
