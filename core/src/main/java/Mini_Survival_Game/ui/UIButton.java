package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.FontManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UIButton extends UIElement {
    private String text;
    private BitmapFont font;
    private OnClickListener clickListener;
    private OnHoverListener hoverListener;

    private boolean hovered;

    @FunctionalInterface
    public interface OnClickListener { void onClick();}

    @FunctionalInterface
    public interface OnHoverListener { void onHover(boolean hovering);}

    public UIButton(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        this.text = text;
        this.font = FontManager.DEFAULT;
    }

    @Override
    protected void renderElement(SpriteBatch batch) {
        font.draw(batch, text, getAbsoluteX(), getAbsoluteY() + getHeight() / 2);
    }

    @Override
    protected boolean onClick(int button) {
        if (clickListener == null) return false;
        clickListener.onClick();
        return true;
    }

    @Override
    protected void onHover(boolean hovering) {
        if (hovered == hovering) {
            return;
        }
        hovered = hovering;

        if (hoverListener != null) {
            hoverListener.onHover(hovering);
        }
    }
}
