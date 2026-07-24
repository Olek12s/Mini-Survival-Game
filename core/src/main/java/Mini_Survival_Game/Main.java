package Mini_Survival_Game;

import Mini_Survival_Game.ui.RelativePositions;
import Mini_Survival_Game.ui.UIButton;
import Mini_Survival_Game.ui.UIManager;
import Mini_Survival_Game.ui.UITable;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture image;

    private OrthographicCamera camera;
    private UIManager uiManager;

    UITable table;
    UIButton buttonA;
    UIButton buttonB;




    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        image = new Texture("libgdx.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiManager = new UIManager(camera);
        Gdx.input.setInputProcessor(uiManager);

        table = new UITable(100, 50, 400, 350, 5, 2);
        UIButton buttonA = new UIButton(100, 50, "SSSSSSSS");
        buttonA.setClickListener(() -> System.out.println("Start clicked!"));
        buttonA.setAlignment(RelativePositions.BOT_RIGHT);
        buttonA.setTextAlignment(RelativePositions.TOP_RIGHT);


        UIButton buttonB = new UIButton(250, 50, 50, 50, "Zażółć Gęślą jaźń");
        table.addElementAt(buttonA, 0, 0);
        table.addElementAt(buttonB, 2, 1);


        uiManager.addRootElement(table);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        uiManager.tick();

        batch.begin();
        batch.draw(image, 140, 210);
        table.render(batch);
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
