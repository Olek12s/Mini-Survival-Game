package Mini_Survival_Game.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class UIManager extends InputAdapter {
    private OrthographicCamera uiCamera;
    private List<UIElement> rootElements = new ArrayList<>();

    public UIManager(OrthographicCamera uiCamera) {
        this.uiCamera = uiCamera;
    }

    public void addRootElement(UIElement element) {
        rootElements.add(element);
    }

    public void tick() {
        for (UIElement element : rootElements) {
            element.tick();
        }
    }

    public void render(SpriteBatch batch) {
        for (UIElement element : rootElements) {
            element.render(batch);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {
        for (UIElement element : rootElements) {
            element.renderDebug(shapeRenderer);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickPosition = new Vector3(screenX, screenY, 0);
        uiCamera.unproject(clickPosition);

        // check root elements as LIFO
        for (int i = rootElements.size() - 1; i >= 0; i--) {
            if (rootElements.get(i).touchDown(clickPosition.x, clickPosition.y, button)) {
                return true;
            }
        }
        return false;   // false if no UI element has consumed touch down
    }
}
