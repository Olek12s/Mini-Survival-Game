package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.FontManager;
import com.badlogic.gdx.graphics.Color;
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

    private RelativePositions textAlignment = RelativePositions.MID;
    private Color textColor = Color.WHITE;

    private Color buttonColor = new Color(0.2f, 0.4f, 0.6f, 1f);
    private Color buttonHoverColor = new Color(0.8f, 0.4f, 0.6f, 0.3f);

    @FunctionalInterface
    public interface OnClickListener { void onClick();}

    @FunctionalInterface
    public interface OnHoverListener { void onHover(boolean hovering);}

    public UIButton(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        this.text = text;
        this.font = FontManager.DEFAULT;
        this.setBackgroundColor(buttonColor);
    }

    public UIButton(float width, float height, String text) {
        super(0, 0, width, height);
        this.text = text;
        this.font = FontManager.DEFAULT;
        this.setBackgroundColor(buttonColor);
    }

    @Override
    protected void renderElement(SpriteBatch batch) {
        if (text == null || text.isEmpty()) return;

        float innerW = getInnerWidth();
        float innerH = getInnerHeight();
        float ax = getAbsoluteX() + paddingLeft;
        float ay = getAbsoluteY() + paddingBottom;

        font.setColor(textColor);
        FontManager.LAYOUT.setText(font, text, textColor, innerW, textAlignment.getHorizontalAlign(), true);

        float textY = textAlignment.calculateTextY(ay, innerH, FontManager.LAYOUT.height);

        font.draw(batch, FontManager.LAYOUT, ax, textY);
    }

    @Override
    protected boolean onClick(int button) {
        if (clickListener == null) return false;
        clickListener.onClick();
        return true;
    }

    @Override
    protected void onHover(boolean hovering) {
        if (hovered == hovering) return;
        hovered = hovering;
        if (hoverListener != null) hoverListener.onHover(hovering);
    }
}
