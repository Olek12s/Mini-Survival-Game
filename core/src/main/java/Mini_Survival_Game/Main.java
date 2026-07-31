package Mini_Survival_Game;

import Mini_Survival_Game.ui.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    Texture panelTex;
    Texture slotTex;

    private OrthographicCamera camera;
    private UIManager uiManager;

    UITable table;
    UIButton buttonA;
    UIButton buttonB;
    UILabel labelA;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        panelTex = new Texture("default_window.9.png");
        slotTex = new Texture("default_window_light.9.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiManager = new UIManager(camera);
        Gdx.input.setInputProcessor(uiManager);

        UIPanel rootPanel = new UIPanel(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new UITable(0, 0, 400, 350, 5, 2);
        table.setAlignment(RelativePositions.MID);

        buttonA = new UIButton(120, 50, "Start!");
        buttonA.setClickListener(() -> System.out.println("Start clicked!"));
        buttonA.setAlignment(RelativePositions.BOT_RIGHT);
        buttonA.setTextAlignment(RelativePositions.TOP_RIGHT);
        buttonA.setPaddingTop(5.f);
        buttonA.setPaddingRight(15.f);
        buttonA.setText("[RED]S[GREEN]t[BLUE]a[YELLOW]r[CYAN]t");

        buttonB = new UIButton(250, 50, 50, 50, "Zażółć Gęślą jaźń");

        table.addElementAt(buttonA, 0, 0);
        table.addElementAt(buttonB, 2, 1);

        labelA = new UILabel("Label");
        labelA.setBackgroundColor(Color.GRAY);
        labelA.setTextAlignment(RelativePositions.BOT_RIGHT);
        labelA.setAlignment(RelativePositions.TOP_RIGHT);

        rootPanel.addElement(labelA);
        rootPanel.addElement(table);

        uiManager.addRootElement(rootPanel);

        NinePatch panelPatch = new NinePatch(panelTex, 10, 10, 10, 10);
        NinePatch cellPatch = new NinePatch(slotTex, 6, 6, 6, 6);

        //rootPanel.setBackgroundPatch(panelPatch);

        table.setBackgroundPatch(panelPatch);
        table.setBorderPatch(cellPatch);
        table.setBorderThickness(1f);
       // table.setAllCellsBackground(cellPatch);


        //table.setCellBackground(0, 0, cellPatch);
        //table.setCellBackground(2, 1, cellPatch);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        uiManager.tick();

        batch.begin();
        uiManager.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //uiManager.renderDebug(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();

        if(panelTex != null) panelTex.dispose();
        if(slotTex != null) slotTex.dispose();
    }
}
