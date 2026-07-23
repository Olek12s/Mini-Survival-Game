package Mini_Survival_Game;

import Mini_Survival_Game.ui.UITable;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture image;

    UITable table = new UITable(100, 100, 100, 100, 2, 5);

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        image = new Texture("libgdx.png");


    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        table.renderDebug(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
