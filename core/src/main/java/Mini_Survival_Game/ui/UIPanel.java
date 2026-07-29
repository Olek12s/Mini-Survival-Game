package Mini_Survival_Game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UIPanel extends UIElement {

    public UIPanel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void addElement(UIElement element) {
        super.addElement(element);
        updateLayout();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        updateLayout();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        updateLayout();
    }

    public void updateLayout() {
        for (UIElement el : elementList) {
            float availableW = getInnerWidth() - el.getMarginLeft() - el.getMarginRight();
            float availableH = getInnerHeight() - el.getMarginTop() - el.getMarginBottom();


            float eW = Math.min(el.getWidth(), availableW);
            float eH = Math.min(el.getHeight(), availableH);

            el.setWidth(eW);
            el.setHeight(eH);

            float targetX = paddingLeft + el.getMarginLeft();
            float targetY = paddingBottom + el.getMarginBottom();

            // Horizontal alignment
            switch (el.getAlignment()) {
                case TOP: case MID: case BOT:
                    targetX += (availableW - eW) / 2f; break;
                case TOP_RIGHT: case MID_RIGHT: case BOT_RIGHT:
                    targetX += (availableW - eW); break;
                default: break; // LEFT
            }

            // Vertical alignment
            switch (el.getAlignment()) {
                case MID_LEFT: case MID: case MID_RIGHT:
                    targetY += (availableH - eH) / 2f; break;
                case TOP_LEFT: case TOP: case TOP_RIGHT:
                    targetY += (availableH - eH); break;
                default: break; // BOT
            }

            el.setX(targetX);
            el.setY(targetY);
        }
    }

    @Override
    protected void renderElement(SpriteBatch batch) {

    }
}