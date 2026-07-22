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
    protected float x, y;
    protected float width, height;

    // Outer margins (outer margins relative to this element's parent)
    protected float marginLeft, marginRight, marginTop, marginBottom;

    // Inner margins (inner margins relative to this element's children)
    protected float paddingLeft, paddingRight, paddingTop, paddingBottom;

    private boolean isVisible = true;
    private boolean isEnabled = true;

    private UIElement parent = null;
    protected List<UIElement> elementList = new ArrayList<>();

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
        if (parent == null) {
            return x + marginLeft;
        }
        return parent.getAbsoluteX() + parent.paddingLeft + x + marginLeft;
    }

    public float getAbsoluteY() {
        if (parent == null) {
            return y + marginBottom;
        }
        return parent.getAbsoluteY() + parent.paddingBottom + y + marginBottom;
    }

    // returns width avaiable for this element's children
    public float getInnerWidth() {
        return width - paddingLeft - paddingRight;
    }

    // returns height avaiable for this element's children
    public float getInnerHeight() {
        return height - paddingTop - paddingBottom;
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

    protected boolean onClick(int button) {return false;}

    public void updateHover(float mouseX, float mouseY) {
        if (!isVisible || !isEnabled)
            return;

        for (UIElement child : elementList) {
            child.updateHover(mouseX, mouseY);
        }
        onHover(contains(mouseX, mouseY));
    }

    protected void onHover(boolean hovering) {}
}
