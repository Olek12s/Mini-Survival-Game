package Mini_Survival_Game.biome.biomes;

import Mini_Survival_Game.biome.Biome;
import Mini_Survival_Game.utilities.noise.Noise;
import com.badlogic.gdx.graphics.Color;

public class River extends Biome {

    public River() {
        super(0f, 0f, 0.7f, 1.4f);
        biomeColor = new Color(20 / 255f, 100 / 255f, 200 / 255f, 1f);
    }


    @Override
    public Color generate(Noise noise, int x, int y) {
        return biomeColor;
    }
}