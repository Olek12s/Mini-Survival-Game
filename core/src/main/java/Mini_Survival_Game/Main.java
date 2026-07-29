package Mini_Survival_Game;

import Mini_Survival_Game.ui.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    UILabel labelA;

    UIPanel rootPanel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        image = new Texture("libgdx.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiManager = new UIManager(camera);
        Gdx.input.setInputProcessor(uiManager);

        rootPanel = new UIPanel(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new UITable(100, 50, 400, 350, 5, 2);
        buttonA = new UIButton(120, 50, "Start!");
        buttonA.setClickListener(() -> System.out.println("Start clicked!"));
        buttonA.setAlignment(RelativePositions.BOT_RIGHT);
        buttonA.setTextAlignment(RelativePositions.TOP_RIGHT);
        buttonA.setPaddingTop(5.f);
        buttonA.setPaddingRight(15.f);
        buttonA.setText("[RED]S[GREEN]t[BLUE]a[YELLOW]r[CYAN]t");


        labelA = new UILabel(100, 80, "Label");
        System.out.println(labelA.getWidth() + ", " + labelA.getHeight() + "', " + labelA.getX() + ", " + labelA.getY());
        labelA.setBackgroundColor(Color.GRAY);
        labelA.setTextAlignment(RelativePositions.BOT_RIGHT);
        labelA.setAlignment(RelativePositions.TOP_RIGHT);
        labelA.pack();


        buttonB = new UIButton(250, 50, 50, 50, "Zażółć Gęślą jaźń");
        table.addElementAt(buttonA, 0, 0);
        table.addElementAt(buttonB, 2, 1);

        rootPanel.addElement(table);
        rootPanel.addElement(labelA);

        uiManager.addRootElement(rootPanel);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        uiManager.tick();

        batch.begin();
        //batch.draw(image, 140, 210);
       // table.render(batch);
       // labelA.render(batch);
      //  rootTable.render(batch);

        uiManager.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
       // table.renderDebug(shapeRenderer);
       // labelA.renderDebug(shapeRenderer);
        // rootTable.renderDebug(shapeRenderer);

        uiManager.renderDebug(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
