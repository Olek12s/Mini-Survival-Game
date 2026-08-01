package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    protected boolean isVisible = true;
    protected boolean isEnabled = true;

    private RelativePositions alignment = RelativePositions.MID;

    protected Color backgroundColor = null;
    protected Texture backgroundTexture = null;
    protected NinePatch backgroundPatch = null;

    protected Color borderColor = null;
    protected Texture borderTexture = null;
    protected NinePatch borderPatch = null;
    protected float borderThickness = 0f;

    private UIElement parent = null;
    protected List<UIElement> elementList = new ArrayList<>();

    public UIElement(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setMargin(float margin) {
        this.marginLeft = margin; this.marginRight = margin;
        this.marginTop = margin; this.marginBottom = margin;
    }

    public void setPadding(float padding) {
        this.paddingLeft = padding; this.paddingRight = padding;
        this.paddingTop = padding; this.paddingBottom = padding;
    }

    public void addElement(UIElement element) {
        if (element != null) {
            element.setParent(this);
            elementList.add(element);
        }
    }

    public float getAbsoluteX() {
        if (parent == null) return x;
        return parent.getAbsoluteX() + x;
    }

    public float getAbsoluteY() {
        if (parent == null) return y;
        return parent.getAbsoluteY() + y;
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

        renderBackground(batch);
        renderElement(batch);
        renderBorder(batch);

        for (UIElement child : elementList) {
            child.render(batch);
        }
    }

    protected void renderBackground(SpriteBatch batch) {
        float ax = getAbsoluteX();
        float ay = getAbsoluteY();

        // Draw background with color
        if (backgroundColor != null) {
            batch.setColor(backgroundColor);
            batch.draw(Renderer.getPixel(), ax, ay, width, height);
        }

        if (backgroundPatch != null) {         // Draw background with 9Patch texture
            batch.setColor(Color.WHITE);
            backgroundPatch.draw(batch, ax, ay, width, height);
        }
        else if (backgroundTexture != null) {  // Draw background with full texture
            batch.setColor(Color.WHITE);
            batch.draw(backgroundTexture, ax, ay, width, height);
        }
        batch.setColor(Color.WHITE);
    }

    protected void renderBorder(SpriteBatch batch) {
        if (borderThickness <= 0) return;
        if (borderColor == null && borderTexture == null && borderPatch == null) return;

        float ax = getAbsoluteX();
        float ay = getAbsoluteY();
        float t = borderThickness;

        batch.setColor(borderColor != null ? borderColor : Color.WHITE);

        if (borderPatch != null) {    // Drawin 9=patch for frame
            borderPatch.draw(batch, ax, ay, width, height);
        }
        else {  // else drawing border with one color
            Texture tex = borderTexture != null ? borderTexture : Renderer.getPixel();
            batch.draw(tex, ax, ay, width, t); // Bottom
            batch.draw(tex, ax, ay + height - t, width, t); // Top
            batch.draw(tex, ax, ay + t, t, height - 2 * t); // Left
            batch.draw(tex, ax + width - t, ay + t, t, height - 2 * t); // Right
        }
        batch.setColor(Color.WHITE);
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

    public boolean scrolled(float mouseX, float mouseY, float amountX, float amountY) {
        if (!isVisible || !isEnabled) return false;

        for (int i = elementList.size() - 1; i >= 0; i--) {
            if (elementList.get(i).scrolled(mouseX, mouseY, amountX, amountY)) {
                return true;
            }
        }
        return false;
    }
}
