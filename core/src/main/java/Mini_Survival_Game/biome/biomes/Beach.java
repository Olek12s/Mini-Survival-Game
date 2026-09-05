package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.biome.Biomes;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class Beach extends Biome {

    // IMPORTANT NOTE FOR MODIFYING BEACH GENERATOR VALUES:
    // continentalness of Beach should be equal half of the Ocean's continentalness,
    // so it can adhere to the shore.
    public Beach() {
        super(0.15f, -0.2f, -0.1f, 0.25f, -0.35f);
        biomeColor = new Color(209 / 255f, 209 / 255f, 109 / 255f, 1f);
    }

    @Override
    public float getGenerationWeight(Noise noise, int tx, int ty) {
        float x = (float)noise.getTemperature(tx, ty) - temperature;
        float y = (float)noise.getHeight(tx, ty) - height;
        float z = (float)noise.getHumidity(tx, ty) - humidity;


        float w = (float)noise.getContinentalness(tx, ty) - continentalness;

        w = w * 2.0f;   // width of the beach - larger multiplier effects with thinner beach

        return rarity / (x * x + y * y + z * z + w * w + 0.000001f);
    }



    @Override
    public Color generate(Noise noise, int x, int y) {
        return null;
    }
}
