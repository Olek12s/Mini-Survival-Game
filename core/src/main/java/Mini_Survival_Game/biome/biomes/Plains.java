package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Plains extends Biome {
    public Plains() {
        super(-0.1f, 0.1f, -0.1f, 1.0f);
        biomeColor = new Color(41 / 255f, 140 / 255f, 56 / 255f, 1f);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
