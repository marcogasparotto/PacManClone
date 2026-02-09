package br.com.marco.pacman.entity;

import br.com.marco.pacman.map.GameMap;
import br.com.marco.pacman.util.Position;

public class YellowGhost extends Ghost {

    public YellowGhost(Position spawn, GameMap map) {
        super(spawn, map, "yellow_ghost");
    }

    @Override
    protected Position getChaseTarget(PacMan pacMan) {

        Position pac = getTile(pacMan.getPosition());
        Position me  = getTile(position);

        int dist =
                Math.abs(pac.x - me.x) +
                        Math.abs(pac.y - me.y);

        // se estiver perto, volta pro canto
        if (dist <= 8) {
            return getTile(spawn);
        }

        // se estiver longe, persegue
        return pac;
    }
}
