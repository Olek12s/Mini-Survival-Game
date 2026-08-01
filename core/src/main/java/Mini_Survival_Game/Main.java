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
    Texture defaultLight;
    Texture defaultDark;


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

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiManager = new UIManager(camera);
        Gdx.input.setInputProcessor(uiManager);

        UIPanel rootPanel = new UIPanel(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        UIScrollPane scrollA = new UIScrollPane(50, 50, 300, 300);
        scrollA.setBackgroundColor(new Color(0.1f, 0.1f, 0.1f, 1f));
        scrollA.setBorderColor(new Color(0.3f, 0.3f, 0.3f, 1f));
        scrollA.setBorderThickness(2f);
        scrollA.setTrackColor(new Color(0.2f, 0.2f, 0.2f, 1f));
        scrollA.setKnobColor(new Color(0.5f, 0.5f, 0.5f, 1f));
        scrollA.setVScrollEnabled(true);
        scrollA.setHScrollEnabled(false);
        scrollA.setVScrollPos(UIScrollPane.ScrollBarPosition.RIGHT);

        UITable innerTable1 = new UITable(0, 0, 280, 800, 10, 1);
        for(int i = 0; i < 10; i++) {
            UIButton b = new UIButton(100, 40, "Cell " + i);
            innerTable1.addElementAt(b, 9-i, 0);
        }
        scrollA.setContent(innerTable1);


        UIScrollPane scrollB = new UIScrollPane(400, 50, 300, 300);
        scrollB.setBackgroundColor(new Color(0.15f, 0.15f, 0.2f, 1f));
        scrollB.setBorderColor(Color.GRAY);
        scrollB.setBorderThickness(2f);
        scrollB.setVScrollEnabled(true);
        scrollB.setHScrollEnabled(true);
        scrollB.setVScrollPos(UIScrollPane.ScrollBarPosition.LEFT);
        scrollB.setHScrollPos(UIScrollPane.ScrollBarPosition.BOTTOM);

        UITable innerTable2 = new UITable(0, 0, 600, 600, 3, 3);
        innerTable2.addElementAt(new UIButton(150, 50, "Left Top"), 2, 0);
        innerTable2.addElementAt(new UIButton(150, 50, "Right Bot"), 0, 2);
        scrollB.setContent(innerTable2);

        uiManager.addRootElement(scrollA);
        uiManager.addRootElement(scrollB);

//        rootPanel.addElement(scrollA);
//        rootPanel.addElement(scrollB);
//        uiManager.addRootElement(rootPanel);
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
