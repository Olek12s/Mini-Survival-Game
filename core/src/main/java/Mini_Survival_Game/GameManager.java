package Mini_Survival_Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameManager extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;


    @Override
    public void create() {

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int x, int y) {
        super.resize(x, y);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
