package Mini_Survival_Game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class UIElement {
    private float x, y;
    private float width, height;

    private boolean isVisible, isEnabled = true;

    private UIElement parent = null;
    private List<UIElement> elementList = new ArrayList<>();

    public UIElement(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addElement(UIElement element) {
        if (element != null) {
            element.setParent(this);
            elementList.add(element);
        }
    }

    public float getAbsoluteX() {
        return (parent == null) ? x : x + parent.getAbsoluteX();
    }

    public float getAbsoluteY() {
        return (parent == null) ? y : y + parent.getAbsoluteY();
    }

    public boolean contains(float cx, float cy) {
        float ax = getAbsoluteX();
        float ay = getAbsoluteY();
        return cx >= ax && cx <= ax + width && cy >= ay && cy <= ay + height;
    }

    public void tick() {
        if (!isVisible) return;
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;
        renderElement(batch);

        for (UIElement child : elementList) {
            child.render(batch);
        }
    }

    protected abstract void renderElement(SpriteBatch batch);

    public void renderDebug(ShapeRenderer shapeRenderer) {
        if (!isVisible) return;

        shapeRenderer.rect(getAbsoluteX(), getAbsoluteY(), width, height);

        for (UIElement child : elementList) {
            child.renderDebug(shapeRenderer);
        }
    }

    /**
     *  returns true if element or its child consumed click
     * @param screenX X position of click
     * @param screenY Y position of click
     * @param button Mouse button
     * @return
     */
    public boolean touchDown(float screenX, float screenY, int button) {
        if (!isVisible || !isEnabled) return false;

        // check children as LIFO
        for (int i = elementList.size() - 1; i >= 0; i--) {
            if (elementList.get(i).touchDown(screenX, screenY, button)) {
                return true;
            }
        }

        if (contains(screenX, screenY)) {
            return onClick(button);
        }

        return false;
    }


    protected boolean onClick(int button) {
        return false;
    }
}
