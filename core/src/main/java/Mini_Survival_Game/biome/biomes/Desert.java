package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Desert extends Biome {
    public Desert() {
        super(0.5f, 0.0f, -0.6f, 1.0f);
        biomeColor = new Color(240 / 255f, 140 / 255f, 91 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
