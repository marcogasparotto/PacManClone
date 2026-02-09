package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.util.Position;

public class OrangeGhost extends Ghost {

    public OrangeGhost(Position spawn, GameMap map) {
        super(spawn, map, "orange_ghost");
    }

    @Override
    protected Position getChaseTarget(PacMan pacMan) {

        Position p = pacMan.getPosition();

        int col = (p.x + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;
        int row = (p.y + GameMap.TILE_SIZE / 2) / GameMap.TILE_SIZE;

        return new Position(col, row);
    }
}
