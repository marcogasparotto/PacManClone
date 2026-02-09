package br.com.marco.pacman.map;

import java.awt.image.BufferedImage;

public class Tile {

    private final TileType type;
    private final BufferedImage sprite;

    public Tile(TileType type, BufferedImage sprite) {
        this.type = type;
        this.sprite = sprite;
    }

    public TileType getType() {
        return type;
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
