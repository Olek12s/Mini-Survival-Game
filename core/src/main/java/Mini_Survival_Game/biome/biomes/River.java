package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class River extends Biome {

    public River() {
        super(0f, 0f, 0.7f, 0.1f);
        biomeColor = new Color(20 / 255f, 100 / 255f, 200 / 255f, 1f);
    }

    @Override
    public float getGenerationWeight(Noise noise, int tx, int ty) {
        double riverVal = noise.getRiverNoise(tx, ty);
        double humidity = noise.getHumidity(tx, ty);
        double height = noise.getHeight(tx, ty);

        double riverWidth = 0.05f;                                          // higher number - larger rivers
        double humidityMod = humidity * 0.09;                                // higher number - larger rivers on humid areas
        double heightMod = (0.5 - height) * 0.09;                           // higher number - smaller rivers on high areas
        double riverThreshold = riverWidth  + humidityMod + heightMod;


        if (riverThreshold > riverWidth ) {
            riverThreshold = riverWidth;
        }

        // River biome wins over every other biome
        if (riverThreshold > 0 && riverVal < riverThreshold) {
            return Float.MAX_VALUE;
        }
        return Float.NEGATIVE_INFINITY; // no river
    }


    @Override
    public Color generate(Noise noise, int x, int y) {
        return biomeColor;
    }
}