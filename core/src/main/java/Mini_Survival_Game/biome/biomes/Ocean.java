package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Ocean extends Biome {
    public Ocean() {
        super(0, -0.6f, 0.2f, 1.f);
        biomeColor = new Color(0 / 255f, 119 / 255f, 190 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
