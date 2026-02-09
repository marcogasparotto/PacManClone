package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.util.Position;

public class LightBlueGhost extends Ghost {

    public LightBlueGhost(Position spawn, GameMap map) {
        super(spawn, map, "lightblue_ghost");
    }

    @Override
    protected Position getChaseTarget(PacMan pacMan) {

        Position pacTile = getTile(pacMan.getPosition());

        int x = pacTile.x;
        int y = pacTile.y;

        switch (pacMan.getDirection()) {
            case UP -> y -= 2;
            case DOWN -> y += 2;
            case LEFT -> x -= 2;
            case RIGHT -> x += 2;
        }

        return new Position(x, y);
    }
}
