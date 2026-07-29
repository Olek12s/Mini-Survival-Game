package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.FontManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UILabel extends UIElement {
    private String text;
    private BitmapFont font;
    private Color textColor = Color.WHITE;

    private RelativePositions textAlignment = RelativePositions.MID_LEFT;
    private boolean wrapText = true;    // if true, moves longer text to the next line

    public UILabel(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        this.text = text;
        this.font = FontManager.DEFAULT;
    }

    public UILabel(float width, float height, String text) {
        this(0, 0, width, height, text);
    }

    public UILabel(String text) {
        this(0, 0, 0, 0, text);
        this.wrapText = false;
        pack();
    }

    public void setText(String text) {
        this.text = text;
        if (!wrapText) {
            pack();
        }
    }

    // Adjust Label's size to the text
    public void pack() {
        if (text != null && font != null) {
            FontManager.LAYOUT.setText(font, text);
            this.width = FontManager.LAYOUT.width;
            this.height = FontManager.LAYOUT.height;
        }
    }

    @Override
    protected void renderElement(SpriteBatch batch) {
        if (text == null || text.isEmpty()) return;

        float ax = getAbsoluteX() + paddingLeft;
        float ay = getAbsoluteY() + paddingBottom;
        float innerW = getInnerWidth();
        float innerH = getInnerHeight();

        font.setColor(textColor);
        FontManager.LAYOUT.setText(font, text, textColor, innerW, textAlignment.getHorizontalAlign(), wrapText);

        float textY = textAlignment.calculateTextY(ay, innerH, FontManager.LAYOUT.height);
        font.draw(batch, FontManager.LAYOUT, ax, textY);
    }
}