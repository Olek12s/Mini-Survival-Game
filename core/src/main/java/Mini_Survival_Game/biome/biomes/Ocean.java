package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Ocean extends Biome {
    public Ocean() {
        super(0, -0.2f, 0.85f, 1f, -0.7f);
        biomeColor = new Color(55 / 255f, 119 / 255f, 190 / 255f, 1f);
    }

    @Override
    public float getGenerationWeight(Noise noise, int tx, int ty) {
        float y = (float)noise.getHeight(tx, ty) - height;
        float w = (float)noise.getContinentalness(tx, ty) - continentalness;

        float continentSmoothness = 2.4f;   // increasing this value should result in more smoother and larger continents and more islands around them
        w = w * continentSmoothness;

        return rarity / (y * y + w * w);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
