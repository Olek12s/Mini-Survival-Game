package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.Renderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UIScrollPane extends UIElement
{
    public enum ScrollBarPosition { LEFT, RIGHT, TOP, BOTTOM}

    private UIElement content;

    private NinePatch trackPatch;
    private NinePatch knobPatch;
    private Color trackColor = new Color(0.2f, 0.2f, 0.2f, 1f); // default track color
    private Color knobColor = new Color(0.6f, 0.6f, 0.6f, 1f);  // default knob color

    private float scrollX = 0f, scrollY = 0f;
    private float maxScrollX = 0f, maxScrollY = 0f;

    private boolean vScrollEnabled = true, hScrollEnabled = false;

    private ScrollBarPosition vScrollPos = ScrollBarPosition.RIGHT;
    private ScrollBarPosition hScrollPos = ScrollBarPosition.BOTTOM;

    private float scrollBarThickness = 15f;
    private float knobMinSize = 20f;
    private float scrollSpeed = 25f;

    private boolean isDraggingH = false, isDraggingV = false;
    private float dragStartX = 0f, dragStartY = 0f;
    private float scrollStartDragX = 0f, scrollStartDragY = 0f;

    private Rectangle vTrackRect = new Rectangle();
    private Rectangle vKnobRect = new Rectangle();
    private Rectangle hTrackRect = new Rectangle();
    private Rectangle hKnobRect = new Rectangle();

    private Rectangle clipBounds = new Rectangle();
    private Rectangle scissors = new Rectangle();

    public UIScrollPane(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void setContent(UIElement content) {
        if (this.content != null) {
            elementList.remove(this.content);
        }
        this.content = content;
        if (this.content != null) {
            addElement(this.content);
        }
        updateLayout();
    }

    public void updateLayout() {
        if (content == null) return;

        float innerW = getInnerWidth();
        float innerH = getInnerHeight();

        if (vScrollEnabled) innerW -= scrollBarThickness;
        if (hScrollEnabled) innerH -= scrollBarThickness;

        maxScrollY = Math.max(0, content.getHeight() - innerH);
        maxScrollX = Math.max(0, content.getWidth() - innerW);

        clampScroll();

        float cx = paddingLeft - scrollX;
        float cy = paddingBottom + innerH - content.getHeight() + scrollY;

        if (hScrollEnabled && hScrollPos == ScrollBarPosition.BOTTOM) {
            cy += scrollBarThickness;
        }
        if (vScrollEnabled && vScrollPos == ScrollBarPosition.LEFT) {
            cx += scrollBarThickness;
        }

        content.setX(cx);
        content.setY(cy);

        calculateScrollBarRects();
    }

    private void clampScroll() {
        if (scrollY < 0) scrollY = 0;
        if (scrollY > maxScrollY) scrollY = maxScrollY;
        if (scrollX < 0) scrollX = 0;
        if (scrollX > maxScrollX) scrollX = maxScrollX;
    }

    private void calculateScrollBarRects() {
        float ax = getAbsoluteX() + paddingLeft;
        float ay = getAbsoluteY() + paddingBottom;
        float innerW = getInnerWidth();
        float innerH = getInnerHeight();

        if (vScrollEnabled && maxScrollY > 0) {
            float tx = (vScrollPos == ScrollBarPosition.RIGHT) ? ax + innerW - scrollBarThickness : ax;
            float ty = ay + (hScrollEnabled && hScrollPos == ScrollBarPosition.BOTTOM ? scrollBarThickness : 0);
            float th = innerH - (hScrollEnabled ? scrollBarThickness : 0);

            vTrackRect.set(tx, ty, scrollBarThickness, th);

            float knobRatio = Math.min(1f, th / (content != null ? content.getHeight() : th));
            float knobH = Math.max(knobMinSize, th * knobRatio);
            float knobY = ty + th - knobH - (scrollY / maxScrollY) * (th - knobH);

            vKnobRect.set(tx, knobY, scrollBarThickness, knobH);
        }
        else {
            vTrackRect.set(0,0,0,0);
            vKnobRect.set(0,0,0,0);
        }

        if (hScrollEnabled && maxScrollX > 0) {
            float tx = ax + (vScrollEnabled && vScrollPos == ScrollBarPosition.LEFT ? scrollBarThickness : 0);
            float ty = (hScrollPos == ScrollBarPosition.TOP) ? ay + innerH - scrollBarThickness : ay;
            float tw = innerW - (vScrollEnabled ? scrollBarThickness : 0);

            hTrackRect.set(tx, ty, tw, scrollBarThickness);

            float knobRatio = Math.min(1f, tw / (content != null ? content.getWidth() : tw));
            float knobW = Math.max(knobMinSize, tw * knobRatio);
            float knobX = tx + (scrollX / maxScrollX) * (tw - knobW);

            hKnobRect.set(knobX, ty, knobW, scrollBarThickness);
        }
        else {
            hTrackRect.set(0,0,0,0);
            hKnobRect.set(0,0,0,0);
        }
    }

    @Override
    public void tick() {
        super.tick();
        updateLayout();
    }

    @Override
    public void updateHover(float mouseX, float mouseY) {
        super.updateHover(mouseX, mouseY);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (isDraggingV) {
                float trackScrollArea = vTrackRect.height - vKnobRect.height;
                if (trackScrollArea > 0) {
                    float deltaY = dragStartY - mouseY;
                    float scrollDelta = (deltaY / trackScrollArea) * maxScrollY;
                    scrollY = scrollStartDragY + scrollDelta;
                    clampScroll();
                }
            }
            else if (isDraggingH) {
                float trackScrollArea = hTrackRect.width - hKnobRect.width;
                if (trackScrollArea > 0) {
                    float deltaX = mouseX - dragStartX;
                    float scrollDelta = (deltaX / trackScrollArea) * maxScrollX;
                    scrollX = scrollStartDragX + scrollDelta;
                    clampScroll();
                }
            }
        }
        else {
            isDraggingV = false;
            isDraggingH = false;
        }
    }

    @Override
    public boolean touchDown(float screenX, float screenY, int button) {
        if (!isVisible || !isEnabled) return false;
        if (!contains(screenX, screenY)) return false;

        if (vScrollEnabled && maxScrollY > 0 && vKnobRect.contains(screenX, screenY)) {
            isDraggingV = true;
            dragStartY = screenY;
            scrollStartDragY = scrollY;
            return true;
        }
        else if (vScrollEnabled && maxScrollY > 0 && vTrackRect.contains(screenX, screenY)) {
            return true;
        }

        if (hScrollEnabled && maxScrollX > 0 && hKnobRect.contains(screenX, screenY)) {
            isDraggingH = true;
            dragStartX = screenX;
            scrollStartDragX = scrollX;
            return true;
        }
        else if (hScrollEnabled && maxScrollX > 0 && hTrackRect.contains(screenX, screenY)) {
            return true;
        }
        return super.touchDown(screenX, screenY, button);
    }

    @Override
    public boolean scrolled(float mouseX, float mouseY, float amountX, float amountY) {
        if (!isVisible || !isEnabled) return false;

        if (contains(mouseX, mouseY)) {
            for (int i = elementList.size() - 1; i >= 0; i--) {
                if (elementList.get(i).scrolled(mouseX, mouseY, amountX, amountY)) return true;
            }

            boolean consumed = false;
            if (vScrollEnabled && maxScrollY > 0 && amountY != 0) {
                scrollY += amountY * scrollSpeed;
                consumed = true;
            }
            if (hScrollEnabled && maxScrollX > 0 && amountX != 0) {
                scrollX += amountX * scrollSpeed;
                consumed = true;
            }

            clampScroll();
            return consumed;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        renderBackground(batch);
        batch.flush();
        clipBounds.set(getAbsoluteX() + paddingLeft, getAbsoluteY() + paddingBottom, getInnerWidth(), getInnerHeight());

        if (UIManager.uiCamera != null) {
            ScissorStack.calculateScissors(UIManager.uiCamera, batch.getTransformMatrix(), clipBounds, scissors);
            if (ScissorStack.pushScissors(scissors)) {

                for (UIElement child : elementList) {
                    child.render(batch);
                }

                batch.flush();
                ScissorStack.popScissors();
            }
        }
        else {
            for (UIElement child : elementList) {
                child.render(batch);
            }
        }

        renderBorder(batch);
        renderScrollbars(batch);
    }

    private void renderScrollbars(SpriteBatch batch) {
        if (vScrollEnabled && maxScrollY > 0) {
            if (trackColor != null) {
                batch.setColor(trackColor);
                batch.draw(Renderer.getPixel(), vTrackRect.x, vTrackRect.y, vTrackRect.width, vTrackRect.height);
            } else if (trackPatch != null) {
                batch.setColor(Color.WHITE);
                trackPatch.draw(batch, vTrackRect.x, vTrackRect.y, vTrackRect.width, vTrackRect.height);
            }

            if (knobColor != null) {
                batch.setColor(knobColor);
                batch.draw(Renderer.getPixel(), vKnobRect.x, vKnobRect.y, vKnobRect.width, vKnobRect.height);
            } else if (knobPatch != null) {
                batch.setColor(Color.WHITE);
                knobPatch.draw(batch, vKnobRect.x, vKnobRect.y, vKnobRect.width, vKnobRect.height);
            }
        }
        if (hScrollEnabled && maxScrollX > 0) {
            if (trackColor != null) {
                batch.setColor(trackColor);
                batch.draw(Renderer.getPixel(), hTrackRect.x, hTrackRect.y, hTrackRect.width, hTrackRect.height);
            } else if (trackPatch != null) {
                batch.setColor(Color.WHITE);
                trackPatch.draw(batch, hTrackRect.x, hTrackRect.y, hTrackRect.width, hTrackRect.height);
            }

            if (knobColor != null) {
                batch.setColor(knobColor);
                batch.draw(Renderer.getPixel(), hKnobRect.x, hKnobRect.y, hKnobRect.width, hKnobRect.height);
            } else if (knobPatch != null) {
                batch.setColor(Color.WHITE);
                knobPatch.draw(batch, hKnobRect.x, hKnobRect.y, hKnobRect.width, hKnobRect.height);
            }
        }
        batch.setColor(Color.WHITE);
    }

    @Override
    protected void renderElement(SpriteBatch batch) {}
}
