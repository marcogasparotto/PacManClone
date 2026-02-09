package br.com.marco.pacman.map;

public enum TileType {
    WALL(1),
    PELLET(2),
    POWER_PELLET(3),
    EMPTY(0),
    TELEPORT(4);

    private final int code;

    TileType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TileType fromCode(int code) {
        for (TileType t : values()) {
            if (t.code == code) return t;
        }
        return EMPTY;
    }

    // Métodos auxiliares
    public boolean isWall() {
        return this == WALL;
    }

    public boolean isPellet() {
        return this == PELLET;
    }

    public boolean isPowerPellet() {
        return this == POWER_PELLET;
    }

    public boolean isTeleport() {
        return this == TELEPORT;
    }
}
