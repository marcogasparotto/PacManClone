package br.com.marco.pacman.map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

public class TileFactory {

    private static final Map<TileType, BufferedImage> sprites = new EnumMap<>(TileType.class);

    static {
        load(TileType.WALL, "/assets/sprites/map/wall.png");
        load(TileType.PELLET, "/assets/sprites/map/pellets.png");
        load(TileType.POWER_PELLET, "/assets/sprites/map/power_pellets.png");
    }

    private static void load(TileType type, String path) {
        try (InputStream is = TileFactory.class.getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Sprite nor found: " + path);
            sprites.put(type, ImageIO.read(is));
        } catch (IOException e) {
            throw new RuntimeException("Error in loading sprites: " + path, e);
        }
    }

    public static BufferedImage getSprite(TileType type) {
        return sprites.get(type);
    }
}
