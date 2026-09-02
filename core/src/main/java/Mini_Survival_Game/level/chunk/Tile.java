package Mini_Survival_Game.level.chunk;

import com.badlogic.gdx.graphics.Color;

public abstract class Tile {
    protected Color color;
    public final String name;
    public short id;

    protected Tile(String name) {
        this.name = name;
    }
}
