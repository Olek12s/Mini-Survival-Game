package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Beach extends Biome {

    public Beach() {
        super(0, -0.7f, 0.2f, 1.f);
        biomeColor = new Color(209 / 255f, 209 / 255f, 109 / 255f, 1f);
    }

    @Override
    public float getGenerationWeight(Noise noise, int tx, int ty) {
        float x = (float)noise.getTemperature(tx, ty) - temperature;
        float y = (float)noise.getHeight(tx, ty) - height;
        float z = (float)noise.getHumidity(tx, ty) - humidity;
        return rarity / (x * x + y + z * z);
    }

    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
