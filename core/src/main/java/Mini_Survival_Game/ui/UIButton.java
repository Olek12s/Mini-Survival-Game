package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.FontManager;
import Mini_Survival_Game.utilities.Renderer;
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
    }

    public UIButton(String text) {
        super(0, 0, 0, 0);
        this.text = text;
        this.font = FontManager.DEFAULT;
    }

    @Override
    protected void renderElement(SpriteBatch batch) {
        float ax = getAbsoluteX();
        float ay = getAbsoluteY();

        // Draw background based on hover
        batch.setColor(hovered ? buttonHoverColor : buttonColor);
        batch.draw(Renderer.getPixel(), ax, ay, width, height);
        batch.setColor(Color.WHITE);

        FontManager.LAYOUT.setText(font, text);
        float textWidth = FontManager.LAYOUT.width;
        float textHeight = FontManager.LAYOUT.height;

        // Center text (taking into account inner padding)
        float innerW = getInnerWidth();
        float innerH = getInnerHeight();

        float textX = ax + paddingLeft + (innerW - textWidth) / 2f;
        float textY = ay + paddingBottom + (innerH + textHeight) / 2f;

        font.draw(batch, text, textX, textY);
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
