package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Forest extends Biome {
    public Forest() {
        super(-0.1f, 0, 0.5f, 1.f);
        biomeColor = new Color(41 / 255f, 115 / 255f, 56 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
