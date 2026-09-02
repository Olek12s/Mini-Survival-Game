package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Beach extends Biome {

    public Beach() {
        super(0, -0.5f, 0, 0);
        biomeColor = new Color(209 / 255f, 209 / 255f, 109 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
