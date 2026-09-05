package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Winter extends Biome {
    public Winter() {
        super(-0.8f, 0.0f, -0.6f, 1.0f);
        biomeColor = new Color(210 / 255f, 210 / 255f, 210 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
