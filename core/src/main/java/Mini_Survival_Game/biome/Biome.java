package Mini_Survival_Game.biome;

import Mini_Survival_Game.biome.biomes.River;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public abstract class Biome {
    protected float temperature;
    protected float height;
    protected float humidity;
    protected float rarity;
    protected float continentalness;

    protected Color biomeColor = Color.WHITE;  // Default biome color. Shall be changed inside biome's constructor

    public Biome(float temperature, float height, float humidity, float rarity) {
        this.temperature = temperature;
        this.height = height;
        this.humidity = humidity;
        this.rarity = rarity;

        // Add created biome to the biomes list
        if (!(this instanceof River)) Biomes.addBiome(this);
    }

    public Biome(float temperature, float height, float humidity, float rarity, float continentalness) {
        this.temperature = temperature;
        this.height = height;
        this.humidity = humidity;
        this.rarity = rarity;
        this.continentalness = continentalness;

        // Add created biome to the biomes list
        if (!(this instanceof River)) Biomes.addBiome(this);
    }

    /**
     *
     *
     * @param noise Generated noise
     * @param x X coordinate
     * @param y Y Coordinate
     * @return Color of the generated surface block
     */
    public abstract Color generate(Noise noise, int x, int y);

    public float getGenerationWeight(Noise noise, int tx, int ty) {
        float x = (float)noise.getTemperature(tx, ty) - temperature;
        float y = (float)noise.getHeight(tx, ty) - height;
        float z = (float)noise.getHumidity(tx, ty) - humidity;
        float w = (float)noise.getContinentalness(tx, ty) - continentalness;

        float continentSize = 1.4f; // larger number - smaller continents

        w = w * continentSize;

        return rarity / (x * x + y * y + z * z + w * w);
    }
}
