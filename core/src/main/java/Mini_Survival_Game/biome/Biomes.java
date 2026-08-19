package Mini_Survival_Game.biome;

import Mini_Survival_Game.biome.biomes.*;
import com.badlogic.gdx.graphics.Color;

public class Biomes {

    ////////////////////////////////////////////////////
    // List of all existing biomes
    ////////////////////////////////////////////////////
    public static final Biome beach = new Beach();
    public static final Biome desert = new Desert();
    public static final Biome forest = new Forest();
    public static final Biome ocean = new Ocean();
    public static final Biome winter = new Winter();

    ////////////////////////////////////////////////////
    // Assigned map colors for biomes
    ////////////////////////////////////////////////////
    public static final Color BEACH_COLOR = new Color(Color.rgba8888(209, 209, 109, 1));
    public static final Color DESERT_COLOR = new Color(Color.rgba8888(240, 240, 91, 1));
    public static final Color FOREST_COLOR = new Color(Color.rgba8888(41, 115, 56, 1));
    public static final Color OCEAN_COLOR = new Color(Color.rgba8888(0, 119, 190, 1));
    public static final Color WINTER_COLOR = new Color(Color.rgba8888(210, 210, 210, 1));


    /**
     * Interactive overview of infinite biome map
     *
     */
    public static void main(String[] args) {

    }
}
